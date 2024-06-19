<script>


// import widgets for this view here

import axios from 'axios';
import {API_URL} from '../config';
import ShortAnswer from '../item_types/ShortAnswer.vue';
import MultipleChoice from '../item_types/MultipleChoice.vue';
import CheckBox from '../item_types/CheckBox.vue';
import SingleChoice from '../item_types/SingleChoice.vue';


export default {
    name: "FormItem",
    data: function () {
        return {
            answer: "",
	    };
	},
    // parameters for this component are: itemId, mode, formId, answer, type
    // mode can be edit, view, submit and create
    // itemId is the id of the item
    // formId is the id of the form
    // answer is the answer to the item which can be bound 
    // type is the type of the item
    props: ["itemId", "mode", "formId", "type"],
    // answer model
    model: {
        prop: 'answer',
        event: 'input'
    },
    mounted: function () 
    {
        axios.get(API_URL + "/forms/")
        .then(response => {
            this.forms = response.data;
        })
        .catch(error => {
            console.log(error);
        });
    },
    methods: {
        deleteItem: function (itemId) {
            axios.delete(API_URL + "/forms/" + this.formId + "/" + itemId)
            .then(response => {
                this.$emit("deleted");
            })
            .catch(error => {
                console.log(error);
            });
        },
        fromSnakeCasetoCamelCase: function (str) {
            return str.replace(/([-_][a-z])/g, (group) =>
                group.toUpperCase()
                .replace('-', '')
                .replace('_', '')
            );
        },
        created: function () {
            this.$emit("created");
        },
        change: function (event) {
            this.$emit("answer", event);
        },
        moveUp: function() {
            axios.post(API_URL + "/forms/"+ this.formId  +"/"  + this.itemId + "/up/")
            .then(response => {
                this.$emit("updated");
            }).catch(error => {
                console.log(error);
            });
        },
        moveDown: function() {
            axios.post(API_URL + "/forms/"+ this.formId  +"/"  + this.itemId + "/down/")
            .then(response => {
                this.$emit("updated");
            }).catch(error => {
                console.log(error);
            });
        }
    },
    components: {
        ShortAnswer: ShortAnswer,
        MultipleChoice: MultipleChoice,
        Checkbox: CheckBox,
        SingleChoice: SingleChoice
    }
}
</script>

<template>

<div class="formy-component">
    <div class="formy-item">
        <component :is="fromSnakeCasetoCamelCase(type)" 
        :itemId="itemId" 
        :mode="mode" 
        :formId="formId" 
        :answer="answer"
        @created="created"
        @answer="change"
    ></component>
    </div>
    <div class="formy-item-actions">
        <button v-if="mode == 'edit' || mode == 'view'" @click="deleteItem(itemId)">Delete</button>
        <button v-if="mode == 'edit' || mode == 'view'" @click="moveUp()">Move Up</button>
        <button v-if="mode == 'edit' || mode == 'view'" @click="moveDown()">Move Down</button>
    </div>
</div>
</template>

<style scoped> 



</style>
