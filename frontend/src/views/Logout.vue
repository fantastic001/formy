<script>

import axios from "axios";
import { API_URL } from "../config"

export default {
    name: "LogOut",
    data: function () {
            return {
		data: {}
	    };
	},
    mounted: function () 
    {
        axios.delete(API_URL + "/login").then(response => {
            localStorage.setItem("user", "");
            this.$store.commit("login", {user: "", role: "NOT_LOGGED"});
            this.$router.push("/");
        }).catch(error => {
            console.log(error);
        });
        
    },
}
</script>

<template>

<p>Logging out...</p>

</template>

<style scoped> 



</style>
