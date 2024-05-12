<script>


// import widgets for this view here

import VueFormGenerator from "vue-form-generator";
import { API_URL } from '../config';
import axios from 'axios';
// on backend, this is DTO to be supplied on API_URL + "/forms/" endpoint via POST request 
	// private LocalDateTime submissionExpiryTime;
	// private String name; 
	// private String description;

export default {
    name: "Form",
    data: function () {
        return {
            data: {},
            model: {
            submissionExpiryTime: '2021-12-31T23:59:59',
            name: 'Form Name',
            description: 'Form Description'
        },
        schema: {
            fields: [
            {
                type: 'input',
                inputType: 'datetime-local',
                label: 'Submission Expiry Time',
                model: 'submissionExpiryTime',
                placeholder: 'Submission Expiry Time',
                featured: true,
                required: true
            },
            {
                type: 'input',
                inputType: 'text',
                label: 'Name',
                model: 'name',
                placeholder: 'Form Name',
                featured: true,
                required: true
            },
            {
                type: 'input',
                inputType: 'text',
                label: 'Description',
                model: 'description',
                placeholder: 'Form Description',
                featured: true,
                required: true
            }
            ]
        },
        formOptions: {
            validateAfterLoad: true,
            validateAfterChanged: true
        }
        };
    },
    mounted: function () 
    {
    },
    methods: {
        submit: function (event) 
        {
            axios.post(API_URL + "/forms/", this.model)
            .then(response => {
                // redirect to home 
                this.$router.push({ name: 'Home' });
            })
            .catch(error => {
                alert("Error: " + error.response.data.message);
            });
            
        }
    },
    components: {
        "vue-form-generator": VueFormGenerator.component
    }
}
</script>

<template>
<div id="home">
<p>Create new form</p>

  <div class="panel-body">
    <vue-form-generator :schema="schema" :model="model" :options="formOptions"></vue-form-generator>
    <button @click="submit">Submit</button>
  </div>
</div>
</template>

<style scoped> 



</style>
