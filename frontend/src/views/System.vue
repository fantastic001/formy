<script>


// import widgets for this view here
import SystemWidget from "../widgets/SystemWidget/SystemWidget.vue"

export default {
    name: "System",
    data: function () {
            return {
		data: {}
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
    	"SystemWidget": SystemWidget
    }
}
</script>

<template>

<SystemWidget/>

</template>

<style scoped> 



</style>
