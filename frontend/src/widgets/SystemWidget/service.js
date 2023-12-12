import { API_URL } from "./../../config";
import axios from "axios"

export default class SystemService
{
    constructor(self) 
    {
        this.self = self;
    }
    

    static accept()
    {
        return axios.get(API_URL + "/service");
    }

   
}
