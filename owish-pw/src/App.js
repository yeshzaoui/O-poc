import React, {Component} from 'react';
import './App.css';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import List from './List';

class App extends Component {

  render() {
    return (
      <div className="App">
        <MuiThemeProvider>
          <div>
            <header className="App-header">
              <h1 className="App-title">Owish</h1>
            </header>
            <List></List>
          </div>
        </MuiThemeProvider>

      </div>
    );
  }
}

export default App;
