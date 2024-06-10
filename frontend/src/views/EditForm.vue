<script>

import axios from 'axios';
import { API_URL } from '../config.js';
import FormItem from "../item_types/FormItem.vue";

// import widgets for this view here

export default {
    name: "EditForm",
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
                this.formItems.sort((a, b) => a.order - b.order);
                // notify about var change 
                this.createType = null;
                this.$forceUpdate();
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
    <div id="edit-form">
        <p>Edit Form {{ data.name }}</p>
        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Add Form Item
        </button>

        <div class="dropdown-menu">
            <button class="dropdown-item" @click="createType = 'short_answer'">Short Answer</button>
            <button class="dropdown-item" @click="createType = 'long_answer'">Long Answer</button>
            <button class="dropdown-item" @click="createType = 'multiple_choice'">Multiple Choice</button>
            <button class="dropdown-item" @click="createType = 'checkbox'">Checkbox</button>
            <button class="dropdown-item" @click="createType = 'dropdown'">Dropdown</button>
            <button class="dropdown-item" @click="createType = 'date'">Date</button>
            <button class="dropdown-item" @click="createType = 'time'">Time</button>
            <button class="dropdown-item" @click="createType = 'file'">File</button>
            <button class="dropdown-item" @click="createType = 'section'">Section</button>
        </div>
        <div v-if="createType != null">
            <FormItem :itemId="null" 
            :mode="'create'" 
            :formId="data.id" 
            :answer="null" 
            :type="createType"
            @created="fetchFormItems"
             />
        </div>
        
        <div v-for="formItem in formItems" :key="formItem.id">
            <FormItem 
                :itemId="formItem.id" 
                :mode="'edit'" 
                :formId="data.id" 
                :answer="formItem.answer" 
                :type="formItem.type"
                @deleted="fetchFormItems"
                @updated="fetchFormItems"
            />
        </div>
    </div>
</template>

<style scoped> 



</style>
