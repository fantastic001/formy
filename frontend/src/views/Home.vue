<script>


// import widgets for this view here

import axios from 'axios';
import {API_URL} from '../config';

// on backend, this is model of form 
	// @Id
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
    // private Long id;
	
	// @NotNull
	// @PastOrPresent
	// private LocalDateTime createTime; 
	
	
	// private LocalDateTime submissionExpiryTime;

	// @ManyToOne
	// @NotNull
	// private User author; 

	// @NotNull
	// private String name; 

	// private String description;

	// @OneToMany
	// Collection<FormSubmission> submissions; 
	

export default {
    name: "Home",
    data: function () {
        return {
            forms: []
	    };
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
      deleteForm: function (formId) {
        // show confirmation first 
        if (confirm("Are you sure you want to delete this form?")) {
          
          axios.delete(API_URL + "/forms/" + formId)
          .then(response => {
              this.forms = this.forms.filter(form => form.id !== formId);
          })
          .catch(error => {
              console.log(error);
          });
        }
      }
    },
}
</script>

<template>
<div id="home">
<p>Home</p>

  <div class="panel-body">
    <!-- show tabular view of forms  -->
    <table class="table table-striped">
      <thead>
        <tr>
          <th>Form Name</th>
          <th>Form Description</th>
          <th>Submission Expiry Time</th>
          <th>Author</th>
          <th>Number of Submissions</th>
          <th>Options</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="form in forms" :key="form.id">
          <td><router-link :to="{ name: 'Form', params: { formId: form.id }}">{{ form.name }}</router-link></td>
          <td>{{ form.description }}</td>
          <td>{{ form.submissionExpiryTime }}</td>
          <td>{{ form.author.username }}</td>
          <td>{{ form.submissions.length }}</td>
          <td>
            <button @click="deleteForm(form.id)" class="btn btn-danger">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</div>
</template>

<style scoped> 



</style>
