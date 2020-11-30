<template>
  <v-container class="rentalintro">
    <v-row justify="center">
        <div class="text-h4 title">대여 내역</div>
    </v-row>
    <v-tabs
      background-color="transparent"
      color="#018F26"
    >
      <v-tab style="color:white" @click="getOngoing()">대여 목록</v-tab>
      <v-tab style="color:white" @click="getPrevious()">이전 대여</v-tab>
    </v-tabs>
    <Ongoing v-if="showOngoing"/>
    <Previous v-if="!showOngoing"/>
  </v-container>
</template>

<script>
import Ongoing from '@/components/rentallist/Ongoing'
import Previous from '@/components/rentallist/Previous'
import http from '@/api/api.js'


export default {
  name: "Inquiry",
  components: {
      Ongoing,
      Previous,
  },
  data() {
    return {
      showOngoing: true,
    }
  },
  methods: {
    getOngoing() {
      this.showOngoing = true
    },
    getPrevious() {
      this.showOngoing = false
    }
  },
  created() {
    http.axios.get('/rentals')
    .then( res => {
      console.log(res)
    })
    .catch( res => {
      console.log(res)
    })
    document.title = 'CatchCam - 대여 내역'
  }
}
</script>

<style scoped>
.rentalintro {
  margin-top: 5vh;
}
.title {
  font-weight: 900;
  height: 50px;
  margin-top: 10vh;
}
.subtitle {
  margin-top: 10vh;
  margin-bottom: 2vh;
  font-size: 25px;
  font-weight: bold;
}
.map_wrap {
  width: 1%;
}
</style>