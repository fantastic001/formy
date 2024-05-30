<script>


// import widgets for this view here

import axios from 'axios';
import {API_URL} from '../config';
import ShortAnswer from '../item_types/ShortAnswer.vue';

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
            axios.delete(API_URL + "/items/" + itemId + "/")
            .then(response => {
                this.$router.push({ name: 'Home' });
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
        change: function () {
            this.$emit('input', this.answer);
            this.$emit("answer", this.itemId, this.answer);
        }
    },
    components: {
        ShortAnswer: ShortAnswer
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
        @input="change"
    ></component>
    </div>
</div>
</template>

<style scoped> 



</style>
