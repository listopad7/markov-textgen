import {Component} from "react/cjs/react.production.min";
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React from 'react';
import {Jumbotron, Spinner, Button} from "reactstrap";
import { connect } from 'react-redux';
import { fetchText } from "./redux/ActionCreators";
import { FaTwitterSquare, FaRedo } from "react-icons/fa"
import { TwitterShareButton} from "react-share";


const mapDispatchToProps = dispatch => ({
    fetchText: () => {dispatch(fetchText())}
})

const mapStateToProps = state => {
    return{
        isLoading: state.isLoading,
        errMess: state.errMess,
        text: state.text
    }
}

function RenderText({isLoading, text, errMess, fetchText})  {
    if (isLoading) {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        <Spinner color="dark">
                            <span className="sr-only">Loading...</span>
                        </Spinner>
                    </div>
                </div>
            </div>
        )
    } else if (errMess) {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-10">
                        <h2>{errMess}</h2>
                    </div>
                </div>
            </div>
        )
    }
    else if (text === null) {
        return(<div className="container">
            </div>
        )
    }else
     {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        <h4>{text}</h4>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        <TwitterShareButton
                            url={'http://listopad-first.bucket.s3-website.eu-central-1.amazonaws.com'}
                            options={{ text: {text} }}>
                            <FaTwitterSquare color="#0057d2"
                                             size="3em"
                                             style={{ padding: "2px" }}
                                             className="primaryBackground" />
                        </TwitterShareButton>
                    </div>
                    <div className="col-12" id="padding-large">
                            <Button onClick={fetchText}>
                                <FaRedo />
                                <b> Try another one</b>
                            </Button>
                        </div>
                </div>
            </div>
        )

    }
}


class App extends Component {

    handle(){

        console.log(this.props);
    }

    render() {
        return (
                <div className="App">
                    <Jumbotron>
                        <div className="container">
                            <div className="row row-header">
                                <div className="col-12">
                                    <h1>Markov text generator</h1>
                                    <p>Generate latin like sentences, and share them on twitter, if you like it!</p>
                                </div>
                                <div className="col-12">
                                    <Button onClick={this.props.fetchText}>Generate</Button>
                                </div>
                            </div>
                        </div>
                    </Jumbotron>
                    <div>
                        <RenderText isLoading={this.props.isLoading} text={this.props.text} errMess={this.props.errMess} fetchText={this.props.fetchText} />
                    </div>
                </div>
        );
    }


}
export default connect(mapStateToProps, mapDispatchToProps)(App);
