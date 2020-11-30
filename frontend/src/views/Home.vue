<template>
  <div id="app">
    
    <v-img
      class="section head-image text-center align-center"
      :src="randomImage"
    >
      <v-btn fab absolute outlined dark class="main-btn" @click="scrollDown(1)"
        ><v-icon x-large>mdi-arrow-down</v-icon></v-btn
      >
    </v-img>

    <v-container class="section d-flex flex-column align-center justify-center" fluid>
      <div>
        <div class="text-h4 my-15">언제 CatchCam을 사용하나요?</div>
      </div>
      <div class="box d-flex justify-center">
        <v-card
        width="300px"
        height="300px"
        color="#23252d"
        flat
        class="d-flex flex-column align-center justify-center mx-3"
        > 
          <v-img
            class="icon"
            src="@/assets/home/011-soccer field.png"
          ></v-img>
          <v-card-title class="white--text explain">
            경기 분석이 필요할 때
          </v-card-title>
        </v-card>
        <v-card
        width="300px"
        height="300px"
        color="#23252d"
        flat
        class="d-flex flex-column align-center justify-center mx-3"
        > 
          <v-img
            class="icon"
            src="@/assets/home/036-soccer.png"
          ></v-img>
          <v-card-title class="white--text explain">
            경기하는 내 모습을 간직하고 싶을 때
          </v-card-title>
        </v-card>
        <v-card
        width="300px"
        height="300px"
        color="#23252d"
        flat
        class="d-flex flex-column align-center justify-center mx-3"
        > 
          <v-img
            class="icon"
            src="@/assets/home/015-team bench.png"
          ></v-img>
          <v-card-title class="white--text explain">
            경기를 녹화해 줄 인력이 부족할 때
          </v-card-title>
        </v-card>
        <v-card
        width="300px"
        height="300px"
        color="#23252d"
        flat
        class="d-flex flex-column align-center justify-center mx-3"
        > 
          <v-img
            class="icon"
            src="@/assets/home/008-yellow card.png"
          ></v-img>
          <v-card-title class="white--text explain">
            경기 판정이 애매할 때
          </v-card-title>
        </v-card>
      </div>

      <v-btn fab absolute outlined dark class="main-btn" @click="scrollDown(2)"
        ><v-icon x-large>mdi-arrow-down</v-icon></v-btn
      >
    </v-container>    

    <v-img
      class="section head-image text-center align-center"
      src="@/assets/main2.png"
    >
      <v-btn fab absolute outlined dark class="main-btn" @click="scrollDown(3)"
        ><v-icon x-large>mdi-arrow-down</v-icon></v-btn
      >
    </v-img>
    <v-img
      class="section head-image text-center align-center"
      src="@/assets/main3.png"
    >
      <v-btn fab absolute outlined dark class="main-btn" @click="scrollDown(4)"
        ><v-icon x-large>mdi-arrow-down</v-icon></v-btn
      >
    </v-img>

    <!-- 사용자 리뷰 -->
    <v-container class="section d-flex flex-column align-center justify-center" fluid>
      <div>
        <div class="text-h4 my-15">실제 사용자들의 후기</div>
      </div>
      <div class="box d-flex justify-center">
        <v-card
        v-for="review in reviews"
        :key="review.id"
        width="300px"
        height="300px"
        color="#23252d"
        flat
        class="d-flex flex-column align-start justify-space-around mx-3"
        > 
          <div class="card-title">
            <v-card-title class="white--text explain">
              {{ review.userName }}
            </v-card-title>
            <v-card-subtitle class="date-text pb-0" style="color:rgb(148, 148, 148);">
              {{ review.strCreateDate }}
            </v-card-subtitle>
            <v-rating
              :value="review.starRate"
              readonly
              color="yellow darken-3"
              background-color="grey darken-1"
              empty-icon="$ratingFull"
              dense
              size="15"
              class="pl-3"
            ></v-rating>
          </div>
            <p class="card-content white--text explain-sm mx-3">{{ review.content }}</p>
        </v-card>
      </div>

      <v-btn fab absolute outlined dark class="main-btn" @click="scrollDown(5)"
        ><v-icon x-large>mdi-arrow-down</v-icon></v-btn
      >
    </v-container>

    <v-container class="section d-flex flex-column justify-space-around" fluid>
      <div class="d-flex justify-center align-center">
        <div class="drone-wrapper">
          <v-img
          class="section head-image text-center align-center"
          style="height:auto;"
          src="@/assets/drone3.png"
          ></v-img>
        </div>
        <div class="mr-15">
          <div class="text-h4">
            CatchCam을 <br>지금 바로 시작해보세요!
          </div>
          <div class="mt-3">
            간단한 절차를 통해 CatchCam을 사용할 수 있습니다. <br> 대여 페이지에서 대여와 관련된 자세한 정보를 확인하세요. 
          </div>
          <div>
            <v-btn
            class="mt-10"
            color="#018F26"
            dark
            rounded
            large
            @click="$router.push({name: 'Login'})"
            >로그인하고 시작하기</v-btn>
          </div>
        </div>
      </div>   
      <Footer/>
      <v-btn fab absolute outlined dark class="main-btn" @click="scrollToTop()"
          ><v-icon x-large>mdi-arrow-up</v-icon>
      </v-btn>
    </v-container>
  </div>
</template>
<script>
import http from '@/api/api.js'
import Footer from '@/components/Footer'


export default {
  name: "Home",
  data() {
    return {
      images: [
        require("@/assets/main.png"),
      ],
      icons: [
        'mdi-facebook',
        'mdi-twitter',
        'mdi-linkedin',
        'mdi-instagram',
      ],
      captions: [
        {
          caption: "경기 분석이 필요할 때",
          image: "@/assets/home/011-soccer field.png",
        },
        {
          caption: "경기하는 내 모습을 간직하고 싶을 때",
          image: "036-soccer"
        },
        {
          caption: "경기를 녹화해 줄 인력이 부족할 때",
          image: "015-team bench"
        },
        {
          caption: "경기 판정이 애매할 때",
          image: "008-yellow card"
        },
      ],
      reviews: []
    };
  },
  components: {
    Footer
  },
  computed: {
    randomImage() {
      let index = Math.floor(Math.random() * this.images.length);
      return this.images[index];
    },
  },
  methods: {
    scrollDown(idx) {
      window.scrollTo({
        top: idx * window.innerHeight,
        behavior: "smooth",
      });
    },
    scrollToTop() {
      window.scrollTo({
        top: 0,
        behavior: "smooth",
      });
    },
  },
  created: function() {
    document.title = 'CatchCam'
    http.axios.get('reviews/recent') 
    .then( res => {
      this.reviews = res.data.result
    })
  }
};
</script>
<style scoped>
@import url(//spoqa.github.io/spoqa-han-sans/css/SpoqaHanSans-kr.css);
* {
  font-family: "Spoqa Han Sans", "Sans-serif";
}
.icon {
  width: auto; height: auto;
  max-height: 100px;
  max-width: 100px;
}
.explain {
  font-size: 17px;
}
.explain-sm {
  font-size: 14px;
}
.intro_title {
  font-size: 24px;
  font-weight: bold;
}
.intro_content {
  padding: 20px 0;
}
.drone-wrapper {
  width: 40%;
}
.section {
  position: relative;
  height: 100vh;
  color: white;
}

.circle {
  width: 20vw;
  height: 20vw;
  background-color: rgb(198 214 242);
  border-radius: 50%;
  margin-top: -40vh;
}

.caption {
  width: 20vw;
}

.head-image {
  /* filter: brightness(70%); */
}

.main-btn {
  bottom: 14vh;
  left: 50%;
  transform: translateX(-50%);
}
.date-text {
  color:rgb(148, 148, 148);
  font-size: 12px;
}
.box {
  margin-bottom: 10rem;
}
.card-title {
  position: absolute;
  top: 1rem;
}
.card-content {
  position: absolute;
  top: 7rem;

  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 8; /* 라인수 */
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
  line-height: 1.2em;
  height: 9.6em;
}
</style>