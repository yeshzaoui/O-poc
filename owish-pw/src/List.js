import React, {Component} from 'react';
import './App.css';
import {Card, CardHeader, CardText} from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import {GridList, GridTile} from 'material-ui/GridList';
// import SockJS from 'SockJS';


const styles = {
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around'
    },
    gridList: {
        width: '50%',
        height: '100%',
        overflowY: 'auto'
    }
};

class List extends Component {

    ipServer = 'localhost';

    constructor() {
        super();
        this.state = {
            items: []
        };
        // var wsocket = new SockJS('`http://${this.ipServer}:8080/rt-owish`');
        // var client = Stomp.over(wsocket);
        // client.connect({}, function(frame) {
        //     client.subscribe('/topic/wishes', function (message) {
        //         // showMessage(JSON.parse(message.body));
        //         alert(message.body);
        //     });
        // });





    }

    getData() {
        return fetch(`http://${this.ipServer}:8080/wishes`).then(response => {
            if (response.ok) {
                return response.json();
            }
            return [];
        }).catch(error => {
            alert(error)
            return [];
        });
    }

    componentWillMount() {
        this
            .getData()
            .then(data => {
                if (data.length > 0) {
                    this.setState({items: data});
                }
            });

    }

    render() {
        return (
            <div className="List">
                <div style={styles.root}>
                    <GridList cellHeight={180} cols={1} style={styles.gridList}>
                        {this
                            .state
                            .items
                            .map((item) => (
                                <GridTile>
                                    <Card>
                                        <CardHeader title={item.title}/>
                                        <Divider/>
                                        <CardText>
                                            {item.content}
                                        </CardText>
                                    </Card>
                                </GridTile>
                            ))}
                    </GridList>
                </div>
            </div>
        );
    }
}

export default List;
