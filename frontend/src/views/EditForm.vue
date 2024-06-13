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
            console.log("Form data: ", this.data);
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
        },
        updateForm: function() {
            axios.post(API_URL + "/forms/" + this.id, this.data)
            .then(response => {
                console.log(response.data);
            })
            .catch(error => {
                console.log(error);
            });
        },
        downloadCsv: function() {
            axios.get(API_URL + "/forms/" + this.id + "/csv")
            .then(response => {
                console.log(response.data);
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', 'form.csv');
                document.body.appendChild(link);
                link.click();
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
        <div class="form-edit">
            <div class="form-group">
                <label for="formName">Form Name</label>
                <input type="text" class="form-control" id="formName" v-model="data.name">
            </div>
            <div class="form-group">
                <label for="formDescription">Form Description</label>
                <textarea class="form-control" id="formDescription" v-model="data.description"></textarea>
            </div>
            <!-- submission expiry time -->
            <div class="form-group">
                <label for="formExpiry">Form Expiry</label>
                <input type="datetime-local" class="form-control" id="formExpiry" v-model="data.submissionExpiryTime">
            </div>
            <button type="button" class="btn btn-primary" @click="updateForm">Update Form</button>
            <button type="button" class="btn btn-primary" @click="downloadCsv">Download CSV</button>
        </div>
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
        
        <div class="formy-formitems">
            <div v-for="formItem in formItems" :key="formItem.id" class="formy-formitem">
                <FormItem 
                    :itemId="formItem.id" 
                    :mode="'view'" 
                    :formId="data.id" 
                    :answer="formItem.answer" 
                    :type="formItem.type"
                    @deleted="fetchFormItems"
                    @updated="fetchFormItems"
                />
            </div>
        </div>
    </div>
</template>

<style scoped> 

.form-edit {
    margin: 20px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: rgb(238, 255, 252);
}

.formy-formitem {
    padding: 50px;
    background-color: rgb(210, 255, 247);
    margin: 20px;
}

.formy-formitems {
    margin: 20px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: rgb(238, 255, 252);
}

</style>
