<template>
  <v-container class="container" fill-height style="max-width:380px">
        <v-laytout align-center justify-center row wrap style="width:100%;">
            <v-flex xs12>
                <v-card class="pa-5">
                    <div class="pa-3 mb-5 d-flex justify-center align-center">
                        <div style="width:25%;">
                            <v-img
                            src="@/assets/drone2.png"
                            width="100%"
                            right
                            center
                            >    
                            </v-img>
                        </div>
                        <div>
                            <h1 class="logo-text">CatchCam</h1>
                            <h3 class="mb-0 grey--text font-weight-light">Catch me if you cam</h3>
                        </div>
                    </div>
                    <div class="pa-3">
                        <v-text-field
                            label="이름"
                            placeholder="이름"
                            v-model="signupData.userName"
                        ></v-text-field>
                        <v-text-field
                            label="이메일"
                            placeholder="이메일"
                            v-model="signupData.email"
                            :hint="error.email"
                        ></v-text-field>
                        <v-text-field
                            label="비밀번호"
                            placeholder="비밀번호"
                            type="password"
                            v-model="signupData.password"
                            :hint="error.password"
                        ></v-text-field>
                        <v-text-field
                            label="비밀번호 확인"
                            type="password"
                            placeholder="비밀번호 확인"
                            v-model="passwordConfirm"
                            :hint="error.passwordConfirm"
                        ></v-text-field>
                    </div>
                    
                    <v-card-actions class="mt-2 d-flex flex-column align-center">
                        <v-btn 
                            class="signup_btn"
                            block
                            large
                            color="#018F26"
                            :disabled="!isComplete"
                            @click="signup(signupData)"
                        >
                            회원가입
                        </v-btn>
                    </v-card-actions>
                    <div class="d-flex justify-center mt-2">
                        <p>이미 캐치캠 회원이신가요? </p>
                        <router-link to="/login" class="ml-3 text-decoration-none">로그인</router-link>
                    </div>
                </v-card>
            </v-flex>
        </v-laytout>
    </v-container>
</template>

<script>
import * as EmailValidator from "email-validator";
import PV from "password-validator";
import http from '@/api/api.js'

export default {
    data() {
        return {
            signupData: {
                email: null,
                password: null,
                userName: null,
            },
            passwordConfirm: null,
            isComplete: false,
            error: {
                email: false,
                password: false,
                passwordConfirm: false,
            },
            passwordSchema: new PV(),
            
        }
    }, 
    created() {
        this.component = this;
        this.passwordSchema
        .is()
        .min(8)
        .is()
        .max(100)
        .has()
        .digits()
        .has()
        .letters()
        document.title = 'CatchCam - 회원가입'
    },
    watch: {
        'signupData.userName': function() {
            this.checkEmail()
            this.checkPassword()
            this.checkPasswordConfirm()
            this.checkAll()
        },
        'signupData.email': function() {
            this.checkEmail()
            this.checkPassword()
            this.checkPasswordConfirm()
            this.checkAll()
        },
        'signupData.password': function() {
            this.checkEmail()
            this.checkPassword()
            this.checkPasswordConfirm()
            this.checkAll()
        },
        'passwordConfirm': function() {
            this.checkEmail()
            this.checkPassword()
            this.checkPasswordConfirm()
            this.checkAll()
        }
    },
    methods: {
        checkEmail() {
           if (this.signupData.email.length >= 0 && !EmailValidator.validate(this.signupData.email)) {
               this.error.email = "이메일 형식이 아닙니다."
           } else {
               this.error.email = false
           }
        },
        checkPassword () {            
            if(this.signupData.password.length >= 0 &&
                !this.passwordSchema.validate(this.signupData.password)) {
                this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다."
            } else {
                this.error.password = false
            }
                
        },
        checkPasswordConfirm () {
            if (this.passwordConfirm.length >= 0 &&
                this.signupData.password != this.passwordConfirm
            ) {
                console.log(this.signupData.password)
                console.log(this.passwordConfirm)
                this.error.passwordConfirm = "동일한 비밀번호를 입력하세요."
            } else {
                this.error.passwordConfirm = false
            }
                
        },
        checkAll() {
            // 모든 값이 차있고, error 모든 값 false
            if (this.signupData.userName.length > 0 && this.signupData.email.length > 0 && this.signupData.password.length > 0 && this.passwordConfirm.length > 0 &&
            !this.error.email && !this.error.password && !this.error.passwordConfirm) {
                this.isComplete = true
                console.log(this.error.passwordConfirm)
            } else {
                this.isComplete = false
                console.log(this.error.passwordConfirm)
            }
        },
        signup(signupData) {
            http.axios.post('/signup', signupData)
                .then(() => {
                    alert("회원가입이 완료되었습니다")
                    this.$router.push({name: 'Home'})
                })
                .catch(err => {
                    console.log(err)
                    alert("이미 가입한 회원입니다")
                })
            }
    },
}
</script>

<style scoped>
.signup_btn {
    color:white;
}
</style>