<script>
import SystemService from "./service";

export default {
	name: "SystemWidget",
	props: ["request"],
    data: function () {
        return {
            
        };
    },
    methods: {
    	accept: function() 
	{
		SystemService.accept().then(response => {

      console.log("PROSLO");
		});
  },

  reject: function() 
	{
	
 

  },

		email: function() 
	{
		
   
	
  },
  
  	decline: function() 
	{
	
	}
	

	},
	
	mounted: function () 
    {
      


    }
}
</script>

<template>
    

  
          <button  v-on:click="accept"  style="background-color:blue;color:white;height:40px;width:200px">
             POKRENI
              </button>
    
    

  


</template>

<style scoped> 

table,td {
   border: 1px solid black;
  border-collapse: collapse;

}
td{
  width: 200px;
  height: 80px;
 
  
}
tr{
  width: 200px;
  height: 80px;
}

</style>
