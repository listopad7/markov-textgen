import {applyMiddleware, createStore} from 'redux';
import { Reducer } from './reducer';
import thunk from 'redux-thunk';
import logger from 'redux-logger';

export const ConfigureStore = () => {
    let store;
    store = createStore(
        Reducer,
        applyMiddleware(thunk, logger)
    );

    return store;
}