<script>

import axios from 'axios';
import { API_URL } from '../config.js';

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
        addItem: function (event) 
        {
            axios.post(API_URL + "/forms/" + this.data.id + "/addFormItem", {
                name: this.createType.name,
                description: this.createType.description,
                type: this.createType.type,
                data: this.createType.data
            })
            .then(response => {
                this.fetchFormItems();
                this.createType = null;
            })
            .catch(error => {
                console.log(error, error.response.data);
            });
        },

        fetchFormItems: function (event) 
        {
            axios.get(API_URL + "/forms/" + this.data.id + "/formItems")
            .then(response => {
                this.formItems = response.data;
            })
            .catch(error => {
                console.log(error);
            });
        },

        shortAnswer: function (event) 
        {
            this.createType = {
                name: "Short Answer",
                description: "A short answer text field",
                type: "short_answer",
                data: {
                    placeholder: "Enter your answer here",
                    required: false
                }
            };
        },

        multipleChoice: function (event) 
        {
            this.createType = {
                name: "Multiple Choice",
                description: "A multiple choice question",
                type: "multiple_choice",
                data: {
                    options: [],
                    required: false
                }
            };
        },

        checkbox: function (event) 
        {
            this.createType = {
                name: "Checkbox",
                description: "A checkbox question",
                type: "checkbox",
                data: {
                    options: [],
                    required: false
                }
            };
        },

        dropdown: function (event) 
        {
            this.createType = {
                name: "Dropdown",
                description: "A dropdown question",
                type: "dropdown",
                data: {
                    options: [],
                    required: false
                }
            };
        },

        fileUpload: function (event) 
        {
            this.createType = {
                name: "File Upload",
                description: "A file upload question",
                type: "file_upload",
                data: {
                    required: false
                }
            };
        },

        date: function (event) 
        {
            this.createType = {
                name: "Date",
                description: "A date question",
                type: "date",
                data: {
                    required: false
                }
            };
        },

        time: function (event) 
        {
            this.createType = {
                name: "Time",
                description: "A time question",
                type: "time",
                data: {
                    required: false
                }
            };
        },

        sectionBreak: function (event) 
        {
            this.createType = {
                name: "Section Break",
                description: "A section break",
                type: "section_break",
                data: {}
            };
        }


    },
    components: {
        
    }
}
</script>

<template>

    <div id="edit-form">
        <p>Edit Form {{ data.name }}</p>
        <!-- button with dropdown menu for multiple form items to add -->
        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Add Form Item
        </button>

        <div class="dropdown-menu">
            <button class="dropdown-item" @click="shortAnswer">Short Answer</button>
            <button class="dropdown-item" @click="multipleChoice">Multiple Choice</button>
            <button class="dropdown-item" @click="checkbox">Checkbox</button>
            <button class="dropdown-item" @click="dropdown">Dropdown</button>
            <button class="dropdown-item" @click="fileUpload">File Upload</button>
            <button class="dropdown-item" @click="date">Date</button>
            <button class="dropdown-item" @click="time">Time</button>
            <button class="dropdown-item" @click="sectionBreak">Section Break</button>
        </div>

        <div v-if="createType != null">
            <!-- add input fields and submit  -->
            <form @submit.prevent="addItem">
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" class="form-control" id="name" v-model="createType.name">
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <input type="text" class="form-control" id="description" v-model="createType.description">
                </div>
                <div class="form-group">
                    <label for="type">Type</label>
                    <input type="text" class="form-control" id="type" v-model="createType.type">
                </div>
                <div class="form-group">
                    <label for="data">Data</label>
                    
                    <div v-if="createType.type == 'shortAnswer'">
                        <label for="placeholder">Placeholder</label>
                        <input type="text" class="form-control" id="placeholder" v-model="createType.data.placeholder">
                        <label for="required">Required</label>
                        <input type="checkbox" class="form-control" id="required" v-model="createType.data.required">
                    </div>

                    <div v-if="createType.type == 'multipleChoice' || createType.type == 'checkbox' || createType.type == 'dropdown'">
                        <label for="options">Options</label>
                        <input type="text" class="form-control" id="options" v-model="createType.data.options">
                        <label for="required">Required</label>
                        <input type="checkbox" class="form-control" id="required" v-model="createType.data.required">
                    </div>

                    <div v-if="createType.type == 'fileUpload'">
                        <label for="required">Required</label>
                        <input type="checkbox" class="form-control" id="required" v-model="createType.data.required">
                    </div>

                    <div v-if="createType.type == 'date' || createType.type == 'time'">
                        <label for="required">Required</label>
                        <input type="checkbox" class="form-control" id="required" v-model="createType.data.required">
                    </div>

                    <div v-if="createType.type == 'sectionBreak'">
                        <!-- no data -->
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>

        <div v-for="formItem in formItems" :key="formItem.id">
            <!-- display form items -->
            <p>{{ formItem.name }}</p>
            <p>{{ formItem.description }}</p>
            <p>{{ formItem.type }}</p>
            <p>{{ formItem.data }}</p>

            <button type="button" class="btn btn-primary">Edit</button>
            <button type="button" class="btn btn-danger">Delete</button>
        </div>
    </div>

</template>

<style scoped> 



</style>
