<template>
  <b-table-simple>
      <b-thead>
      <b-tr>
          <b-th  v-for="key in columns" :key="key">
              {{ key }}
          </b-th >
      </b-tr>
      </b-thead>

    <b-tbody>
        <b-tr v-for="entry in students" :key="entry.id">
                    <b-td><a href="" :click ="getStudent(entry.id)"> {{entry.firstName}} </a></b-td>
                    <b-td>{{entry.lastName}}</b-td>
                    <b-td>{{entry.classID}}</b-td>
        </b-tr>
    </b-tbody>
  </b-table-simple>
</template>

<script>
import Api from "../Api"

const studentTable = {
name: 'studentTable',
data: function(){
  return {
    students: [],
    columns: ['First Name', 'Last Name', 'Class'],
  }
},
mounted() {
  Api.getAll()
  .then(response => {
    this.students = response.data
    console.log(this.students)
  })
},

methods:{
getStudent: function(itemID){
            Api.getItem(itemID)
            }
}
}
  
export default studentTable  
</script>

<style>

</style>