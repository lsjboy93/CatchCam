<template>
  <div class="mb-10">
    <div class="d-flex justify-space-between align-center">
      <div class="d-flex">
        <div class="score_text" v-if="!isNull">
          이용후기 <span class="colored_text">{{ count }}개</span>
        </div>
        <div class="score_text" v-if="isNull">
          이용후기 <span class="colored_text">0개</span>
        </div>
        <div class="mx-2">
          ·
        </div>
        <div class="score_text" v-if="!isNull">
          평균평점 <span class="colored_text">{{ avg_rate }}점</span>
        </div>
        <div class="score_text" v-if="isNull">
          평균평점 <span class="colored_text">0점</span>
        </div>
      </div>
    </div>
    <div v-if="!isNull">
      <ReviewItem
      :drone="drone"
      class="mt-3"
      v-for="review in reviews"
      :key="review.id"
      :review="review"
      @deleteReview="reload()"
      />
    </div>
    <div class="text-center mt-15">
      <v-pagination
        color="#23252d"
        circle
        dark
        v-model="page"
        :length="pageCount"
        prev-icon="mdi-menu-left"
        next-icon="mdi-menu-right"
      ></v-pagination>
    </div>
  </div>
</template>

<script>
import ReviewItem from '@/components/droneintro/ReviewItem'
import http from '@/api/api.js'

export default {
  name: "Reviews",
  components: {
    ReviewItem,
  },
  props: {
    drone: {
      type: Object
    }
  },
  data() {
    return {
      isNull: true,
      reviews: [],
      reviewCount: 1,
      page: 1,
      pageCount: 1
    }
  },
  mounted: function() {
    http.axios.get('/reviews/1', { params: {page: this.page }}) 
    .then( res => {
      if (res.data.result.reviewCount % 5 === 0) {
        this.pageCount = Math.floor(res.data.result.reviewCount / 5)
      } else {
        this.pageCount = Math.floor(res.data.result.reviewCount / 5 + 1)
      }
      
      if(res.data.result) {
        this.isNull = false
      }
      this.avg_rate = res.data.result.avgRate,
      this.count = res.data.result.reviewCount,
      this.review_yn = res.data.result.reviewYn
      this.reviews = res.data.result.reviews
    })
  },
  watch: {
    page() {
      http.axios.get('/reviews/1', { params: {page: this.page} })
      .then( res => {
        this.reviews = res.data.result.reviews
        if (res.data.result.reviewCount % 5 === 0) {
          this.pageCount = Math.floor(res.data.result.reviewCount / 5)
        } else {
          this.pageCount = Math.floor(res.data.result.reviewCount / 5 + 1)
        }
      })
    } 
  },
  methods: {
    reload() {
      this.$router.go()
    }
  }

}
</script>

<style scoped>
.score_text {
  font-size: 18px;
}
.reg_text {
  color: white;
}
.colored_text {
  color: #018F26;
  font-weight: bold;
}
</style>