<script>

import axios from 'axios';
import { API_URL } from '../config.js';
import FormItem from "../item_types/FormItem.vue";

// import widgets for this view here

export default {
    name: "SubmitForm",
    data: function () {
            return {
		data: {},
        formItems: [],
        createType: null
	    };
	},
    props: {
        id: {
            type: Number,
            required: true
        }
    },
    mounted: function () 
    {
        axios.get(API_URL + "/forms/" + this.id)
        .then(response => {
            this.data = response.data;
            this.fetchFormItems();
        })
        .catch(error => {
            console.log(error);
        });
    },
    methods: {
        fetchFormItems: function() {
            axios.get(API_URL + "/forms/" + this.id + "/formItems")
            .then(response => {
                console.log(response.data);
                this.formItems = response.data;
                // notify about var change 
                this.$forceUpdate();
            })
            .catch(error => {
                console.log(error);
            });
        },

        submitForm: function() {
            // get all answers from form items 
            var answers = {};
            for (var i = 0; i < this.formItems.length; i++) {
                var formItem = this.formItems[i];
                answers[formItem.id] = formItem.answer;
            }

            axios.post(API_URL + "/forms/" + this.id + "/submit", answers)
            .then(response => {
                this.$router.push({ name: 'Home' });
            })
            .catch(error => {
                console.log(error);
            });
        }

    },
    components: {
        FormItem: FormItem
    }
}
</script>

<template>
    <div id="submit-form">
        
        <div v-for="formItem in formItems" :key="formItem.id">
            <FormItem 
                :itemId="formItem.id" 
                mode="submit" 
                :formId="data.id" 
                :answer="formItem.answer" 
                :type="formItem.type"
            />
        </div>

        <button @click="submitForm">Submit</button>
    </div>
</template>

<style scoped> 



</style>
