<script>


// import widgets for this view here

import axios from 'axios';
import {API_URL} from '../config';

export default {
    name: "ShortAnswer",
    data: function () {
        return {
            item: {},
            createType: null
	    };
	},
    props: ["itemId", "mode", "formId", "answer"],
    mounted: function () 
    {
        if (this.mode == "create") {
            this.setup();
        }
        else {
            this.getItem();
            if (this.mode == "submit") {
                this.answer = "";
            }
            else if (this.mode == "view") {
                this.answer = this.item.data;
            }
            else if (this.mode == "edit") {
                this.setup();
                this.createType.data = this.item.data;
                this.createType.name = this.item.name;
                this.createType.description = this.item.description;
            }
            else {
                console.log("Invalid mode");
            }
        }
    },
    methods: {
        deleteItem: function (itemId) {
            axios.delete(API_URL + "/items/" + itemId + "/")
            .then(response => {
                this.$router.push({ name: 'Home' });
            })
            .catch(error => {
                console.log(error);
            });
        },
        getItem: function() {
            axios.get(API_URL + "/forms/" + this.formId + "/formItems" )
                .then(response => {
                    var items = response.data;
                    for (var i = 0; i < items.length; i++) {
                        if (items[i].id == this.itemId) {
                            this.createType = items[i];
                            break;
                        }
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },
        addItem: function (event) 
        {
            axios.post(API_URL + "/forms/" + this.formId + "/addFormItem", {
                name: this.createType.name,
                description: this.createType.description,
                type: this.createType.type,
                data: this.createType.data
            })
            .then(response => {
                // EMIT "CREATED" SIGNAL 
                this.$emit('created', response.data);
                this.setup();
            })
            .catch(error => {
                console.log(error, error.response.data);
            });
        },
        setup: function (event) 
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
    }
}
</script>

<template>

<div class="formy-item formy-component">

    <!-- add input fields and submit  -->
    <form @submit.prevent="addItem" v-if="mode == 'create'">
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
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <!-- display item -->
    <div v-else-if="mode == 'view'">
        <p>{{ createType.name }}</p>
        <p>{{ createType.description }}</p>
        <p>{{ createType.type }}</p>
        <p>{{ createType.data }}</p>
    </div>
    <!-- edit item -->
    <div v-else-if="mode == 'edit'">
        <form @submit.prevent="addItem">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" v-model="createType.name">
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <input type="text" class="form-control" id="description" v-model="createType.description">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div> 
    <div v-if="mode == 'submit'">
        <!-- put name for label and description as tooltip  -->
        <label>{{ createType.name }}</label>
        <input type="text" class="form-control" v-model="answer" :placeholder="createType.data.placeholder" />
        <p>{{ createType.description }}</p> 
    </div>
    <div v-else>
        <p>Mode not supported</p>
    </div>



</div>

</template>

<style scoped> 



</style>
