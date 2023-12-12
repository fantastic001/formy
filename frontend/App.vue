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
    <nav class="main-menu navbar navbar-dark bg-primary navbar-expand-lg">
	<div class="navbar-nav">
        <router-link to='/'>Home</router-link>

        <router-link v-if='data.role == "NOT_LOGGED" || data.role == null ' to='/login'>Login</router-link>
        <router-link v-if='data.role == "NOT_LOGGED" || data.role == null ' to='/register'>Registration</router-link>
        <router-link v-if='data.role == "DOCTOR" || data.role == "NURSE"' to='/patients'>Patients</router-link>
        <router-link v-if='data.role == "DOCTOR" || data.role == "NURSE" || data.role == "CLINIC_ADMINISTRATOR"' to='/CareList'>Cares</router-link>


        <router-link v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR"' to='/regreq'>Registration Requests</router-link>
        <router-link v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR"' to='/careTypes'>Care Types</router-link>
        <router-link v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR" ||  data.role == "DOCTOR"|| data.role == "NURSE"' to='/profile'>My Profile</router-link>
        <router-link v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR" ||  data.role == "DOCTOR"|| data.role == "NURSE"|| data.role == "PATIENT"' to='/updateUser'>Update profile</router-link>
		<router-link v-if="data.role == 'PATIENT'" to='/patientMedicalRecord'>Medical record</router-link>
		<router-link v-if="data.role == 'PATIENT'" to='/patientCareHistory'>Care history</router-link>
		<router-link v-if="data.role == 'PATIENT'" to='/patientOperationHistory'>Operation history</router-link>
        <router-link v-if="data.role == 'ADMINISTRATOR'" to='/clinic-new'>Create Clinic</router-link>
       <!--<router-link v-if="data.role == 'DOCTOR'" to='/doctor/myPatients'>My Patients</router-link>-->
        <router-link v-if='data.role == "DOCTOR" || data.role == "CLINIC_ADMINISTRATOR"' to='/schedule/operation'>Schedule Operation</router-link>

		<router-link v-if="data.role == 'ADMINISTRATOR' " to='/addAdminClinic'>Add Admin</router-link>

        <router-link v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR" ||  data.role == "DOCTOR"|| data.role == "NURSE"|| data.role == "PATIENT"' to='/clinics'>Show all clinics</router-link>
		<router-link v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR"' to='/addMedicine'>Add Medicine</router-link>
		<router-link v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR"' to='/addDiagnosis'>Add Diagnosis</router-link>
		<router-link v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR" || data.role =="DOCTOR" || data.role =="NURSE"' to='/calendar'>Calendar</router-link>
		
		<router-link v-if='data.role == "DOCTOR"' to='/changeoldReview'>Change old review</router-link>
        <router-link v-if="data.role == 'CLINIC_ADMINISTRATOR'" to='/createCare'>Add new care time</router-link>
	<router-link v-if="data.role == 'DOCTOR' || data.role == 'CLINIC_ADMINISTRATOR' || data.role == 'NURSE'" to="/vrm">Vacation Requests</router-link>
		<router-link v-if="data.role =='NURSE'" to='prescription'>Authenticate Prescriptions</router-link>

        <router-link v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR"' to='/reports'>My Clinic Reports</router-link>
        <router-link v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR" ||  data.role == "DOCTOR"|| data.role == "NURSE"|| data.role == "PATIENT"' to='/logout'>Log out</router-link>




	</div>
	
	<p class="my-2 my-lg-0" v-if='data.role == "ADMINISTRATOR" || data.role == "CLINIC_ADMINISTRATOR" ||  data.role == "DOCTOR"|| data.role == "NURSE"|| data.role == "PATIENT"'>Logged as: {{ data.user }}</p>


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

.main-menu p
{
	color: white;
}
</style>
