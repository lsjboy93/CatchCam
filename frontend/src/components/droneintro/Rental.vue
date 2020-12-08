<template>
  <v-card max-width="400" class="rental-card">
      <v-card
        outlined
        max-width="400"
        class="mx-auto d-flex flex-column justify-center"
      >         
        <v-card-subtitle class="subtitle">지점 선택</v-card-subtitle>
        <v-virtual-scroll
          :items="stores"
          item-height="64"
          v-if="stores"
        > 
          
          <template v-slot:default="{ item }">
            <v-list-item :key="item.storeId" class="store_item" @click="getStore(item.storeId)" v-bind:class="{clicked: applyData.storeId === item.storeId }">
              <v-list-item-content>
                <v-list-item-title>
                  <strong>{{ item.storeName }}</strong> {{ item.telNo }}
                </v-list-item-title>
                <v-list-item-title class="sm-content">
                  {{ item.address }}
                </v-list-item-title>
              </v-list-item-content>

              <v-list-item-action>
                <v-btn
                  text
                  depressed
                  @click="mapDialog=true; showMap(item);"
                > 
                <div class="icon-wrap">
                  <v-img
                    src="@/assets/map.png"
                  ></v-img>
                </div>
                </v-btn>
              </v-list-item-action>
            </v-list-item>

            <v-divider></v-divider>
          </template>
        </v-virtual-scroll>

        <v-card-subtitle class="subtitle mt-5">날짜 선택</v-card-subtitle>
        <div class="d-flex justify-center">
          <v-card class="btn-text" @click="getCalendar()">
            <v-card-title class="btn-title">시작일</v-card-title>
            <v-card-subtitle v-if="dates[0]">{{dates[0]}}</v-card-subtitle>
            <v-card-subtitle v-else>날짜추가</v-card-subtitle>
          </v-card>
          <v-card class="btn-text" @click="getCalendar()">
            <v-card-title class="btn-title">반납일</v-card-title>
            <v-card-subtitle v-if="dates[1]">{{dates[1]}}</v-card-subtitle>
            <v-card-subtitle v-else>날짜추가</v-card-subtitle>
          </v-card>
        </div>
        <!-- calendar -->
        <v-card-text>
          <v-row v-if="showCalendar" justify="center">
              <v-date-picker
                full-width
                v-model="dates"
                range
                header-color="#018F26"
                color="#018F26"
              ></v-date-picker>
          </v-row>
        </v-card-text>
        <!-- 대여료 -->
        <div v-if="cost">
          <v-card-subtitle class="subtitle">가격</v-card-subtitle>
          <v-card-text class="d-flex flex-column">
            <div class="d-flex justify-sm-space-between">
              <div>
                대여료 
              </div>
              <strong>
                ₩{{ drone.cost | comma }} × {{ term }}일
              </strong>
            </div>
            <hr class="my-3">
            <div class="emp-text align-self-end">
              ₩{{ cost | comma }}
            </div>
          </v-card-text>
        </div>

        <v-btn
        class="ma-3 my-5"
        color="#018F26"
        dark
        large
        @click="apply()"
        >대여 신청하기</v-btn>
      </v-card>

      <v-dialog
        v-model="mapDialog"
        persistent
        max-width="600"
      >
        <v-card>
          <v-toolbar color="#018F26" dark flat class="d-flex justify-center">
            <v-toolbar-title>
              {{selectedStore.storeName}}
            </v-toolbar-title>
          </v-toolbar>
          <v-divider></v-divider>
          <v-card-text class="py-0">
            <GmapMap
            ref="mapRef"
            :center="center"
            :zoom="16"
            style="width:100%; height:400px">
              <GmapMarker
              :position="center"
              >
              </GmapMarker>
            </GmapMap>
            </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="rgb(148, 148, 148)"
              outlined
              @click="mapDialog = false"
              width="70"
              rounded
              small
            >
              닫기
            </v-btn>
          </v-card-actions>
        </v-card>

      </v-dialog>

  </v-card>
</template>

<script>
import http from '@/api/api.js'

export default {
  name: "Rental",
  data() {
    return {
      dates: [],
      term: null,
      cost: null,
      showCalendar: false,
      stores: null,
      applyData: {
        itemId: this.drone.itemId,
        storeId: null,
        rentDate: null,
        returnDate: null,
      },
      mapDialog: false,
      selectedStore:{
        
      },
      center: {

      }
    };
  },
  props: {
    drone: {
      type: Object,
    }
  },
  filters: {
    comma: function(value) {
      var num = new Number(value);
      return num.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")
    }
  },
  watch: {
    'dates': function() {
      // 시작일 오늘 날짜 이후로
      let today = new Date()
      var arr1 = this.dates[0].split('-')
      this.applyData.rentDate = new Date(arr1[0], arr1[1], arr1[2])
      var currDay = 24 * 60 * 60 * 1000

      if (parseInt((this.applyData.rentDate - today)/currDay) < 30) {
        alert(`${today.toISOString().substring(0, 10)} 이후부터 대여 가능합니다.`)
        this.dates = []
        this.applyData.rentDate = null 
        this.cost = null
      }
      // 반납일 시작일 이후로 
      if (this.dates[1]) {
        this.getCalendar()
        var arr2 = this.dates[1].split('-')
        this.applyData.returnDate = new Date(arr2[0], arr2[1], arr2[2])
        
        this.term = parseInt((this.applyData.returnDate - this.applyData.rentDate)/currDay)
        if (this.term < 0) {
          alert('반납일은 대여일 이후로 지정해주세요.')
          this.dates.pop()
          this.applyData.returnDate = null
          this.cost = null
        } else {
          this.cost = this.term * this.drone.cost 

        }
      }
    }
  },
  created: function() {
    http.axios.get('/stores')
    .then( res => {
      this.stores = res.data.result
    })
  },
  computed: {
    dateRangeText () {
      return this.dates.join(' ~ ')
    },
    items () {
        return Array.from({ length: this.length }, (k, v) => v + 1)
    },
    length () {
      return 5
    },
  },
  methods: {
    getCalendar() {
      this.showCalendar = !this.showCalendar
    },
    getStore(store_id) {
      this.applyData.storeId = store_id
    },
    apply() {
      // 로그인 여부 확인
      if (!sessionStorage.getItem('Token')) {
        alert('로그인 후 이용해주세요.')
        this.$router.push({name: 'Login'})
      } else {
        if(!this.applyData.storeId) {
          alert('대여할 지점을 선택해주세요')
        } else if (!this.applyData.rentDate || !this.applyData.returnDate) {
          alert('날짜를 선택해 주세요')
        } else {
          http.axios.post('/rental', this.applyData) 
          .then (() => {
            alert('대여 예약이 완료되었습니다. 정확한 날짜에 방문하여 물건을 수령해주세요.')
            this.$router.push({name: 'RentalList'})
          })
          .catch(err => {
            console.log(err)
          })
        }
      }
    },
    showMap(item){
      this.selectedStore = item;
      this.center = {lat: this.selectedStore.latitude, lng: this.selectedStore.longitude};
    }
  }

}
</script>

<style scoped>
.btn-title {
  font-size: 15px;
  color: black;
  font-weight: bold;

}
.btn-text {
  color: rgb(148, 148, 148);
  width:45%;
  cursor:pointer;
}
.subtitle {
  font-size: 17px;
  color: black;
  font-weight: bold;
}
.sm-content {
  font-size: 13px;
}
.icon-wrap {
  width: 80%;
}
.emp-text {
  color: #018F26;
  font-size: 20px;
  font-weight: bold;
}
.store_item {
  cursor: pointer;
}
.clicked {
  background-color: rgb(228, 228, 228);
}

</style>