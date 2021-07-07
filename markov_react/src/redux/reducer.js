import * as ActionTypes from "./ActionTypes";


export const Reducer = (state = {errMess: null, isLoading: false,  text:null}, action) => {
    switch (action.type) {
        case ActionTypes.ADD_TEXT:
            return {...state, isLoading: false, errMess: null, text: action.payload};

        case ActionTypes.TEXT_FAILED:
            return {...state, isLoading: false, errMess: action.payload};

        case ActionTypes.TEXT_LOADING:
            return {...state, isLoading: true, errMess: null, text: ''}

        default:
            return state;
    }
};