import { API_URL } from "./../../config";
import axios from "axios"

export default class RegistrationFormService 
{
    constructor(self) 
    {
        this.self = self;
    }


    static submit(x)
    {
        return axios.post(API_URL + "/users/", x);
    }
}
