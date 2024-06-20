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
        createType: null,
        answers: {},
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
                this.formItems.forEach(item => {
                    this.answers[item.id] = "";
                });
                this.formItems.sort((a, b) => a.order - b.order);
                // notify about var change 
                this.$forceUpdate();
            })
            .catch(error => {
                console.log(error);
            });
        },

        answer: function(event) {
            var itemId = event.itemId;
            var answer = event.answer;
            this.answers[itemId] = answer;
        },

        submitForm: function() {
            
            console.log(this.answers);
            axios.post(API_URL + "/forms/" + this.id + "/submit", this.answers)
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
                :answer="answers[formItem.id]" 
                :type="formItem.type"
                @answer="answer"
            />
        </div>

        <button class="btn-submit" @click="submitForm">Submit</button>
    </div>
</template>

<style scoped> 

.btn-submit {
    margin-top: 20px;
    margin-left: 20px;
    margin-bottom: 20px;
    height: 40px;
    font-size: 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    /* make it wide over whole width */
    display: block;
    width: 100%;
}

</style>
