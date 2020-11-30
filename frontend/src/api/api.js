import Vue from "vue"
import axios from "axios";

export default new Vue({
  computed: {
    token() {
       return sessionStorage.getItem('Token');
    },
    axios() {
      return axios.create({
        baseURL: "https://catchcam.site/api",
        headers: {
          "x-auth-token": this.token ? this.token : '',
          "Content-type": "application/json"
        }
      });
    }
  } 
}); 