<template>
  <div class="header d-flex justify-space-between">
    <div>
      <v-img
      class="logo ma-3"
      src="@/assets/logo2.png"
      @click="$router.push({name: 'Home'})"
      ></v-img>
    </div>
    <div>
      <div class="d-flex align-center mt-5" style="height:50px">
      <v-btn
        v-show="!isRentalPage"
        color="#ffffff"
        dark
        rounded
        large
        outlined
        @click="$router.push({name: 'DroneIntro'})"
        >대여하기</v-btn>

        <v-menu offset-y>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              class="mr-5"
              text
              v-bind="attrs"
              v-on="on"
            >
              <v-img
                src="@/assets/menu.png"
              ></v-img>
            </v-btn>
          </template>
          <!-- 로그인x -->
          <div v-if="!isLogin">
            <v-list class="d-flex flex-column">
              <v-btn 
                text
                :to="{name: 'Login'}"
              >
                <v-list-item>
                  <v-list-item-title class="pr-15 text-left btn_text">
                    로그인
                    </v-list-item-title>
                </v-list-item>
              </v-btn>
              <v-btn 
                text
                :to="{name: 'Signup'}"
              >
                <v-list-item>
                  <v-list-item-title class="pr-15 text-left btn_text">회원가입</v-list-item-title>
                </v-list-item>
              </v-btn>
            </v-list>
          </div>
          <!-- 로그인 -->
          <div v-if="isLogin">
            <v-list class="d-flex flex-column">
              <v-btn 
                text
                :to="{name: 'RentalList'}"
                large
              >
                <v-list-item>
                  <v-list-item-title class="pr-15 text-left btn_text">
                    <strong>대여 내역</strong>
                    </v-list-item-title>
                </v-list-item>
              </v-btn>
              <v-divider></v-divider>
              <v-btn 
                text
                large
              >
                <v-list-item>
                  <v-list-item-title class="pr-15 text-left btn_text">계정</v-list-item-title>
                </v-list-item>
              </v-btn>
              <v-btn 
                text
                @click="logout()"
                large
              >
                <v-list-item>
                  <v-list-item-title class="pr-15 text-left btn_text">로그아웃</v-list-item-title>
                </v-list-item>
              </v-btn>
            </v-list>
          </div>
        </v-menu>
      
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Header',
  data() {
    return {
      isRentalPage: false,
    }
  },
  methods: {
    logout() {
      sessionStorage.removeItem('Token')
      this.$router.push({name: 'Home'})
      this.$router.go()
    },
    checkRentalPage() {
      if(this.$router.currentRoute.name === 'DroneIntro') {
        console.log(this.$router.currentRoute)
        this.isRentalPage = true
      } else {
        console.log(this.$router.currentRoute)
        this.isRentalPage = false
      }
    }
  },
  computed: {
    isLogin() {
      return sessionStorage.getItem('Token') ? sessionStorage.getItem('Token') : false
    },
  },
  created() {
    this.checkRentalPage()
  },
  watch: {
    // $route(to, from) {
    //   if (to.path != from.path) { 
    //     console.log('router변경')
    //     this.checkRentalPage()
    //   } 
    // },
    $route() {
      this.checkRentalPage()
    }
    
  }
}
</script>

<style scoped>
.logo {
  width: 200px;
  cursor: pointer;
}
.header {
  position: fixed;
  z-index: 8000;
  width: 100%;
}
.btn_text {
  font-size: 14px;
}
</style>