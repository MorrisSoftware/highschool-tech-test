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
        <b-tr v-for="student in students" :key="student.id">
                    <b-td>{{student.firstName}}</b-td>
                   <b-td>{{student.lastName}}</b-td>
                    <b-td>{{student.classID}}</b-td>
                    <b-td v-for="testScore in student.marks" :key="testScore.id">{{ testScore.subject + ':' +  testScore.score}}</b-td>
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
    columns: ['First Name', 'Last Name', 'Class', 'Marks'],
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