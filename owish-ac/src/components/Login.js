import React, { Component } from 'react';
import {Card, CardActions, CardHeader, CardTitle, CardText} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import TextField from 'material-ui/TextField';
import './Login.css'

import AuthService from './AuthService';

class Login extends Component {

    constructor(){
        super();
        this.handleChange = this.handleChange.bind(this);
        this.handleClick = this.handleClick.bind(this);
        this.Auth = new AuthService();
    }

    componentWillMount(){
        if(this.Auth.loggedIn())
            this.props.history.replace('/');
    }

    handleChange(e){
        this.setState(
            {
                [e.target.name]: e.target.value
            }
        )
    }

    handleClick(e){
        e.preventDefault();
      
        this.Auth.login(this.state.username,this.state.password)
            .then(res =>{
               this.props.history.replace('/');
            })
            .catch(err =>{
                alert(err);
            })
    }

    
    render() {
      return (
        <div className="Login-card">
        <Card>
            <CardHeader title="Welcome" />
            <CardTitle title="Please login" />
            <CardText>
                <TextField floatingLabelText="Username" name="username" onChange={this.handleChange} />
                <br />
                <TextField hintText="Password" floatingLabelText="Password" name="password" type="password" onChange={this.handleChange} />
            </CardText>
            <CardActions>
                <FlatButton label="Login" onClick={this.handleClick} />
            </CardActions>
        </Card>
        </div>
      );
    }

  }
  
  export default Login;