<template>
  <v-container fluid>
    <v-row>
      <v-col
        class="pa-0"
        md="4"
        lg="3"
        v-for="rentalItem in rental_list"
        :key="rentalItem.id"
      >
        <RentalItem
        :rentalItem="rentalItem"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import RentalItem from '@/components/rentallist/RentalItem'
import http from '@/api/api.js'


export default {
  name: "Previous",
  components: {
    RentalItem
  },
  data() {
    return {
      rental_list: []
    }
  },
  created: function() {
    http.axios.get('/rentals') 
    .then (res => {
      // status가 예약중, 대여중인 값만 받기
      var arr = res.data.result
      for (var i = 0; i < arr.length; i ++) {
        if (arr[i].status === '반납완료') {
          this.rental_list.push(arr[i])
        }
      }
      // console.log(this.rental_list)
    })
    .catch (err => {
      console.log(err)
    })
  }
}
</script>

<style scoped>

</style>