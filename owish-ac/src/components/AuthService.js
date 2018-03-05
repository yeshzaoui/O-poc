import decode from 'jwt-decode';


export default class AuthService {

    constructor(domain) {
        this.domain = domain || 'http://localhost:8080'
        this.fetch = this.fetch.bind(this)
        this.login = this.login.bind(this)
        // this.getProfile = this.getProfile.bind(this)
    }

    login(username, password) {
        let params = {
            username: username,
            password: password,
        };
        
        let formData = new URLSearchParams();
        
        for (let k in params) {
            formData.append(k, params[k]);
        }
        
        let request = {
            method: 'POST',
            body: formData
        };

        return this.fetch(`${this.domain}/users/signin`, request).then(res => {
            this.setToken(res.token)
            return Promise.resolve(res);
        })
    }

    setToken(idToken) {
        // Saves user token to localStorage
        localStorage.setItem('id_token', idToken)
    }

    fetch(url, options) {
        // performs api calls sending the required authentication headers
        const headers = {
            'Content-Type': 'application/x-www-form-urlencoded'
        }

        if (this.loggedIn()) {
            headers['Authorization'] = 'Bearer ' + this.getToken()
        }

        return fetch(url, {
            headers,
            ...options
        }).then(this._checkStatus).then(response => response.json())
    }

    getToken() {
        // Retrieves the user token from localStorage
        return localStorage.getItem('id_token');
    }

    _checkStatus(response) {
        // raises an error in case response status is not a success
        if (response.status >= 200 && response.status < 300) {
            return response
        } else {
            var error = new Error(response.statusText)
            error.response = response
            throw error
        }
    }

    loggedIn() {
        // Checks if there is a saved token and it's still valid
        const token = this.getToken()
        return !!token && !this.isTokenExpired(token) // handwaiving here
    }

    isTokenExpired(token) {
        try {
            const decoded = decode(token);
            if (decoded.exp < Date.now() / 1000) {
                return true;
            }
            else
                return false;
        }
        catch (err) {
            return false;
        }
    }

    logout() {
        // Clear user token and profile data from localStorage
        localStorage.removeItem('id_token');
    }

    getProfile() {
        return decode(this.getToken());
    }

}