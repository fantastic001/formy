<script>


// import widgets for this view here
import WidgetUserSingle from "../widgets/User/WidgetUserSingle.vue"

export default {
    name: "Profile",
    data: function () {
            return {
		data: {
            id: null
        },
		role: localStorage.getItem("role"),
		user: localStorage.getItem("user_id")
	    };
	},
    mounted: function () 
    {
    },
    methods: {
        submit: function (event) 
        {
            
        }
    },
    components: {
    	WidgetUserSingle
    }
}
</script>

<template>

<div>

<p class="p">
<WidgetUserSingle  :user="this.user" />
</p>
</div>
</template>


<style scoped> 
.p{
    position:fixed;
    top:20%;
    left:40%;
	padding: 10px; 
	margin: 10px;
	text-align: center;
	width: 20%;
}


</style>
