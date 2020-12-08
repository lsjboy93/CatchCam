<template>
  <v-card
    color="#23252d"
    class="ma-3 my-card"
    height="450"
    
  >
    <!-- {{ rentalItem.rentalId }} -->
    <template slot="progress">
      <v-progress-linear
        color="deep-purple"
        height="10"
        indeterminate
      ></v-progress-linear>
    </template>
    <v-img
      height="250"
      src="@/assets/drone4.png"
    ></v-img>
    <v-card-text class="py-0">
      <v-row
        align="center"
        class="mx-0"
      >
        <div class="grey--text font-weight-light">
          {{ rentalItem.strRentDate }} ~ {{ rentalItem.strReturnDate }}
        </div>
      </v-row>
    </v-card-text>
    <div class="d-flex justify-space-between align-center">
      <div class="d-flex align-center">
        <v-card-title class="white--text py-0 pr-2">
          {{ rentalItem.itemName }}
          <v-chip class="ml-2" small color="#018F26" outlined>{{ rentalItem.status }}</v-chip>
        </v-card-title>
      </div>
      <div class="white--text my-1 mx-5" style="font-size:1.2rem;">
        ₩{{ cost | comma }}
      </div>
    </div>

    <!-- <v-divider class="mx-4"></v-divider> -->
    <v-card-actions class="d-flex flex-column">
      <v-btn
        text
        block
        class="white--text d-flex justify-space-between"
      >
      <div>{{ rentalItem.storeName }}</div>
      <div class="gray-text font-weight-light">자세히 보기 ></div>
        
      </v-btn>
    
    </v-card-actions>
    <v-card-actions class="d-flex flex-row-reverse">
      <div v-if="rentalItem.status=='예약중'" class="ma-3">
        <v-btn
          outlined
          color="red"
          @click="cancel()"
          small
        >
          예약 취소
        </v-btn>
      </div>
      <div v-if="rentalItem.status=='대여중' || rentalItem.status=='반납완료'" class="ma-3">
        <v-dialog
          v-model="dialog"
          persistent
          max-width="500"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn 
              v-if="rentalItem.reviewYn==='N'"
              color="rgb(148, 148, 148)"
              outlined
              v-bind="attrs"
              v-on="on"
              small
            >
              후기 작성
            </v-btn>
          </template
          >
          <v-card>
            <v-toolbar color="#018F26" dark flat class="d-flex justify-center">
              <v-toolbar-title>
                후기 작성
              </v-toolbar-title>
            </v-toolbar>
            <v-card-text class="d-flex align-center py-0">
              <div class="img-wrap">
                <v-img
                  src="@/assets/drone4.png"
                ></v-img>
              </div>
              <div class="d-flex flex-column align-start">
                <div class="gray-text">
                  {{ rentalItem.storeName }}
                </div>
                <v-card-subtitle class="pa-0">
                  <strong>{{ rentalItem.itemName }}</strong>
                </v-card-subtitle>
                <div class="gray-text">
                  {{ rentalItem.strRentDate }} ~ {{ rentalItem.strReturnDate }}
                </div>
              </div>
            </v-card-text>
            <v-divider></v-divider>

            <v-card-text class="py-2 d-flex flex-column align-center">
              <div class="mr-1">
                평점
              </div>
              <v-rating
                color="yellow darken-3"
                background-color="grey darken-1"
                empty-icon="$ratingFull"
                half-increments
                hover
                dense
                size="30"
                v-model="reviewData.starRate"
              ></v-rating>
              <div v-if="reviewData.starRate" class="gray-text">
                {{ reviewData.starRate }}점 
              </div>
            </v-card-text>
            <v-card-text class="py-0">
              <v-textarea
                color="rgb(148, 148, 148)"
                counter="5000"
                flat
                filled
                placeholder="최소 10자 이상 입력해주세요."
                v-model="reviewData.content"
              ></v-textarea>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="rgb(148, 148, 148)"
                outlined
                @click="dialog = false"
                width="100"
                rounded
              >
                취소
              </v-btn>
              <v-btn
                dark
                color="green darken-1"
                @click="dialog = false; createReview()"
                class="ma-2"
                width="100"
                rounded
              >
                등록
              </v-btn>
            </v-card-actions>
          </v-card>

        </v-dialog>
      </div>
    </v-card-actions>
  </v-card>
</template>

<script>
import http from '@/api/api.js'


export default {
  name: "RentalItem",
  props: {
    rentalItem: {
      type: Object
    }
  },
  data () {
    return {
      dialog: false,
      reviewData: {
        content: null,
        rentalId: this.rentalItem.rentalId,
        starRate: null,
      },
      cost: 0
    }
  },
  methods: {
    createReview() {
      http.axios.post('/review', this.reviewData)
      .then(() => {
      })
      .catch( err => {
        console.log(err)
      })
    },
    cancel() {
      http.axios.delete(`/rental/${this.rentalItem.rentalId}`)
      .then( () => {
        alert('예약이 취소되었습니다.')
        this.$router.push({name: 'RentalList'})
        this.$router.go()
      })
      .catch( err => {
        alert(err.data.result)
        // console.log(err)
      })
    }
  },
  created: function() {
  var d1 = this.rentalItem.strReturnDate
  var d2 = this.rentalItem.strRentDate
  var arr1 = d1.split('-')
  var arr2 = d2.split('-')
  
  var returnDate = new Date(arr1[0], arr1[1], arr1[2])
  var rentDate = new Date(arr2[0], arr2[1], arr2[2])
  var currDay = 24 * 60 * 60 * 1000

  var term = parseInt((returnDate - rentDate)/currDay)
  this.cost = term * this.rentalItem.cost
  },
  filters: {
    comma: function(value) {
      var num = new Number(value);
      return num.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")
    }
  },
}
</script>

<style scoped>
.title {
  font-size: 2rem;
  font-weight: bold;
}
.gray-text {
  font-size: 0.8rem;
  color: rgb(148, 148, 148);
}
.my-img {
  background-color: white;
}
.my-card {
  cursor: pointer;
}
.img-wrap {
  width: 40%;
}
</style>