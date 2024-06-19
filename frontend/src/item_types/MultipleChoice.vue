<script>


// import widgets for this view here

import axios from 'axios';
import {API_URL} from '../config';

export default {
    name: "MultipleChoice",
    data: function () {
        return {
            item: {},
            createType: {},
            answer: "",
            option_text: "",
            selected: [],
        };
	},
    props: ["itemId", "mode", "formId"],
    model: {
        prop: 'answer',
        event: 'input'
    },
    mounted: function () 
    {
        if (this.mode == "create") {
            this.setup();
        }
        else {
            this.getItem();
            if (this.mode == "submit") {
                console.log("Data: ", this.createType);
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
        change: function (event) {
            var option = event.target.value;
            if (this.selected.includes(option)) {
                this.selected = this.selected.filter(item => item !== option);
            }
            else {
                this.selected.push(option);
            }
            // answer is comma-separated values from selected options
            this.answer = this.selected.join(",");

            this.$emit('answer', {
                itemId: this.itemId,
                answer: this.answer
            });
        },
        getItem: function() {
            axios.get(API_URL + "/forms/" + this.formId + "/formItems" )
                .then(response => {
                    var items = response.data;
                    console.log("ItemId: " + this.itemId + " Items: ", items);
                    for (var i = 0; i < items.length; i++) {
                        if (items[i].id == this.itemId) {
                            console.log("Item: ", items[i]);
                            this.createType = {
                                name: items[i].name,
                                description: items[i].description,
                                type: items[i].type,
                                data: {
                                    options: items[i].data.options.split(",")
                                }
                            }
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
                data: {
                    options: this.createType.data.options.join(",")
                }
            })
            .then(response => {
                // EMIT "CREATED" SIGNAL 
                this.$emit('created');
                this.setup();
            })
            .catch(error => {
                console.log(error, error.response.data);
            });
        },
        setup: function (event) 
        {
            this.createType = {
                name: "Multiple Choice",
                description: "Select one of the options",
                type: "multiple_choice",
                data: {
                    options: [],
                }
            };
        },
        addOption: function (event) 
        {
            this.createType.data.options.push(this.option_text);
            this.option_text = "";
        },
        removeOption: function (index) 
        {
            this.createType.data.options.splice(index, 1);
        }
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
            
            <div class="form-group">
                <label for="option_text">Option</label>
                <input type="text" class="form-control" id="option_text" v-model="option_text">
                <button type="button" class="btn btn-primary" @click="addOption">Add Option</button>
            </div>
            <div v-for="(option, index) in createType.data.options" :key="option">
                <div class="form-group
                ">
                    <p>{{ option }}</p>
                    <button type="button" class="btn btn-danger" @click="removeOption(index)">Remove</button>
                </div>
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
    <div v-else-if="mode == 'submit'">
        <p>{{ createType.description }}</p>
        <div class="form-group">
            <label for="answer">Answer</label>
            <!-- add checkboxes -->
            <div v-for="(option, index) in createType.data.options" :key="option">
                <div class="form-check">
                    <input class="form-check" type="checkbox" :id="option" :value="option" @change="change">
                    <label class="form-check" :for="option">{{ option }}</label>
                </div>
            </div>
        </div>
    </div>
    <div v-else>
        <p>Mode not supported</p>
    </div>



</div>

</template>

<style scoped> 



</style>
