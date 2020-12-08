<template>
    <v-container class="container" fill-height style="max-width:380px">
        <v-laytout align-center justify-center row wrap style="width:100%;">
            <v-flex xs12>
                <v-card flat class="pa-5">
                    <!-- 로고 -->
                    <div class="pa-3 d-flex justify-center align-center">
                        <v-img
                        src="@/assets/logo1.png"
                        width="100%"
                        right
                        center
                        >    
                        </v-img>
                    </div>
                    <!-- 폼 -->
                    <div class="pa-3">
                        <v-text-field
                        v-model="email"
                        label="이메일"
                        ></v-text-field>
                        <v-text-field
                        v-model="password"
                        type="password"
                        label="비밀번호"
                        ></v-text-field>
                        <v-checkbox
                        v-model="saveId"
                        label="아이디 저장"
                        color="#018F26"
                        value="primary"
                        hide-details
                        class="mt-0"
                        ></v-checkbox>
                    </div>
                    <!-- 버튼 -->
                    <v-card-actions class="">
                        <v-btn 
                            class="login_btn"
                            block
                            large
                            color="#018F26"
                            @click="login(email, password)"
                            :disabled="!isComplete"
                        >
                            로그인
                        </v-btn>
                    </v-card-actions>
                    <div class="d-flex justify-center mt-2">
                        <router-link to="/signup" class="text-decoration-none grey--text">회원가입</router-link>
                        <router-link to="" class="ml-3 text-decoration-none grey--text">아이디·비밀번호 찾기</router-link>
                    </div>
                </v-card>
            </v-flex>
        </v-laytout>
    </v-container>
</template>

<script>
import http from "@/api/api.js"

export default {
    data() {
        return {
            email: null,
            password: null,
            saveId: false,
            isComplete: false,
        }
    },
    watch: {
        'email': function() {
            this.checkAll()
        },
        'password': function() {
            this.checkAll()
        }
    },
    methods: {
        checkAll() {
            if (this.email && this.password) {
                this.isComplete = true
            } else {
                this.isComplete = false
            }
        },
        login(email, password) {
            http.axios.post('/login', {
                email: email,
                password: password
            })
            .then(res => {
                sessionStorage.setItem("Token", res.data.result)
                this.$router.push({name: 'DroneIntro'})
                this.$router.go()
            })
            .catch(() => {
                alert('가입하지 않은 이메일이거나, 잘못된 비밀번호입니다.')
            })
        }
    },
    created() {
        document.title = 'CatchCam - 로그인'
    }    
}
</script>

<style scoped>
.logo-text {
    font-size: 2em;
}
.login_btn {
    color:white;
}
</style>