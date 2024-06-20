<script>

import UserService from "./src/widgets/User/service";

export default {
  name: "App",
  data: function () {
	return {
		data: {
			user: localStorage.getItem("user"),
			role: localStorage.getItem("role")
		}
	}
  },
  components: {

  },
  methods:{
	  logOut: function() 
		{
			UserService.logOut().then(response => {
				window.location.href = "/frontend/";
			});
		}
  },
  mounted: function () {
	this.$store.subscribe((mutation, state) => {
		console.log("Logged as: ", state.user, " with role: ", state.role);
		console.log(this);
		this.data.user = state.user;
		this.data.role = state.role;
		console.log("State changed");
	})
  }
}

</script>


<template>
  <div id="app">
    <nav class="navbar navbar-dark navbar-expand-lg main-menu">
	<div class="navbar-nav">
        <router-link to='/'>Home</router-link>

        <router-link v-if='data.role == "NOT_LOGGED" || data.role == null ' to='/login'>Login</router-link>
        <router-link v-if='data.role == "NOT_LOGGED" || data.role == null ' to='/register'>Registration</router-link>

        
        <router-link v-if='data.role != "NOT_LOGGED" && data.role != null' to='/logout'>Log out</router-link>
		<router-link v-if='data.role != "NOT_LOGGED" && data.role != null' to='/form'>Create Form</router-link>

		<span class="user-status" v-if='data.role != "NOT_LOGGED" && data.role != null'>Logged as: {{ data.user }}</span>



	</div>
	


    </nav>
    <div class="view"><router-view /> </div>
  </div>
</template>

<style scoped>

.view {
	margin: 20px;
}

.main-menu a
{
	color: white;
	padding: 10px;
}


.main-menu a:hover 
{
	text-decoration: none;
	display: block;
	background: #bbbbff;
}


.main-menu
{
	color: white;
	background-color: #03264d;
	display: block;
	width: 100%;
}

.user-status {
	/* put it on right side of navbar */
	margin-left: auto;
	/* move it on right  */
	margin-right: 10px;
	display: block;

}


</style>
