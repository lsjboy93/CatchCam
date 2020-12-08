<template>
  <div>
    <hr class="my-hr">
    <div class="mt-5 mb-7">
      <div class="d-flex justify-space-between">
        <div class="d-flex align-center my-3">
          <div>{{ review.userName }}</div>
          <div class="date-text">{{ review.strCreateDate }}</div>
          <v-rating
            :value="review.starRate"
            readonly
            color="yellow darken-3"
            background-color="grey darken-1"
            empty-icon="$ratingFull"
            dense
            size="15"
            class="ml-5"
          ></v-rating>
        </div>

        <!-- 수정/삭제 버튼 -->
        <div v-if="review.writerYn == 'Y'" class="text-center">
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="#8d8d8d" icon dark v-bind="attrs" v-on="on">
                <v-icon>mdi-dots-vertical</v-icon>
              </v-btn>
            </template>
            <v-list>
              
              <v-list-item class="pa-0">
                <!-- 수정 폼 -->
                <v-dialog
                  v-model="dialog"
                  persistent
                  max-width="500"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn 
                      v-bind="attrs"
                      v-on="on"
                      text
                      block
                      class="pa-0"
                    >
                      수정
                    </v-btn>
                  </template
                  >
                  <v-card>
                    <v-toolbar color="#018F26" dark flat class="d-flex justify-center">
                      <v-toolbar-title>
                        후기 수정
                      </v-toolbar-title>
                    </v-toolbar>
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
                        v-model="updateData.starRate"
                      ></v-rating>
                      <div v-if="updateData.starRate" class="gray-text">
                        {{ updateData.starRate }}점 
                      </div>
                    </v-card-text>
                    <v-card-text class="py-0">
                      <v-textarea
                        color="rgb(148, 148, 148)"
                        counter="5000"
                        flat
                        filled
                        placeholder="최소 10자 이상 입력해주세요."
                        v-model="updateData.content"
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
                        @click="dialog = false; updateReview()"
                        class="ma-2"
                        width="100"
                        rounded
                      >
                        등록
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-list-item>
              <v-list-item class="pa-0">
                <v-btn flat text @click="del()" block >삭제</v-btn>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </div>

      <div class="pr-10 content">
        {{ review.content }}
      </div>
    </div>
  </div>
</template>

<script>
import http from '@/api/api.js'


export default {
  name: "ReviewItem",
  props: {
    review: {
      type: Object
    },
    drone: {
      type: Object,
    }
  },
  data() {
    return {
      dialog: false,
      updateData: {
        content: this.review.content,
        reviewId: this.review.reviewId,
        starRate: this.review.starRate,
      }
    }
  },
  methods: {
    del() {
      http.axios.delete(`/review/${this.review.reviewId}`)
      .then( () => {
        this.$emit('deleteReview')
        alert('리뷰가 삭제되었습니다.')
      })
    },
    updateReview() {
      http.axios.put('/review', this.updateData)
      .then ( () => {
        this.$router.go()
        alert('리뷰가 수정되었습니다.')
      })
    }
  },
}
</script>

<style scoped>
.date-text {
  color:rgb(148, 148, 148);
  margin-left: 1rem;
  font-size: 14px;
}
.my-hr {
  border: 0;
  height: 1px;
  background-color:rgb(148, 148, 148);
}
.content {
  font-size: 14px;
}

</style>