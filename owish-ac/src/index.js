import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import './index.css';
import App from './App';
import Login from './components/Login';
import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(
    <MuiThemeProvider>
        <Router>
            <div>
            <Route exact path="/" component={App} />
            <Route exact path="/login" component={Login} />
            </div>
        </Router>
    </MuiThemeProvider>
    , document.getElementById('root'));
registerServiceWorker();
