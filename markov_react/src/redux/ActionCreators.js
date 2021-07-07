import * as ActionTypes from './ActionTypes';

const baseUrl = 'https://sp5ei6fsi7.execute-api.eu-central-1.amazonaws.com/beta/MarkovTextGenerator'

export const addText = (text) => ({
    type: ActionTypes.ADD_TEXT,
    payload: text
});

export const textLoading = () => ({
    type: ActionTypes.TEXT_LOADING
});

export const textFailed = (errorMessage) => ({
    type: ActionTypes.TEXT_FAILED,
    payload: errorMessage
});

export const fetchText = () => (dispatch) => {
    dispatch(textLoading());

    return fetch(baseUrl, {
        mode: "cors"
    })
        .then(response => {
            if (response.ok) {
                return response;
            } else {
                let error = new Error('Error ' + response.status + ': ' + response.statusText);
                error.response = response;
                throw error;
            }
        },
            error => {
                throw new Error(error.message);


        })
        .then(response => response.json())
        .then(text => dispatch(addText(text)))
        .catch(error => dispatch(textFailed(error.message)));


}