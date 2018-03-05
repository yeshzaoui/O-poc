import React, { Component } from 'react';
import {Card, CardActions, CardTitle, CardText} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import TextField from 'material-ui/TextField';
import './App.css';
import withAuth from './components/withAuth';

class App extends Component {

  constructor(){
    super();
    this.handleChange = this.handleChange.bind(this);
    this.handleClick = this.handleClick.bind(this);
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
    
    let body = {'title':this.state.title, 'content':this.state.content};
    this.addData(body).then(data => alert(`the wish ${data.title} was added successfully`))
  }

  addData(data) {

    let url = 'http://localhost:8080/wishes'
    let request = {
      method: 'POST',
      body: JSON.stringify(data)
    };
    const headers = {
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + localStorage.getItem('id_token')
    }
    return fetch(url, {headers, ...request}).then(response => {
      if (response.status === 201) {
        return response.json();
      }
      return;
    }).catch(error => {
      alert(error)
  });
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">O'wish Admin Console</h1>
        </header>
        <div>
        <Card>
            <CardTitle title="Add New Wish" />
            <CardText>
                <TextField floatingLabelText="Title" name="title" onChange={this.handleChange} />
                <br />
                <TextField floatingLabelText="Content" name="content" onChange={this.handleChange} />
            </CardText>
            <CardActions>
                <FlatButton label="Submit" onClick={this.handleClick} />
            </CardActions>
        </Card>
        </div>
      </div>
    );
  }
}

export default withAuth(App);
