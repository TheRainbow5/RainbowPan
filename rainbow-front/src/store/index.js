// 导入 vue和vuex,并把vuex挂载到vue实例
import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)



const store = new Vuex.Store({
    // 存储全局变量
    state: {
        filePid: undefined,   //当前目录
    },
    // 修改全局变量必须通过mutations中的方法
    mutations: {
        /**
         * 存储当前目录
         * @param {*} state 
         * @param {传入的参数} payload 
         */
        saveFilePid(state, payload) {
            state.filePid = payload;
            console.log(state.filePid);
        },
    },
    // 异步方法用actions
    // actions不能直接修改全局变量，需要调用commit方法来触发mutations中的方法
    actions: {
        /**
         * 
         * @param {mutations的方法名} context 
         * @param {参数} payload 
         */
        saveFilePid(context, payload) {
            //调用mutations中的login方法，传入参数payload
            context.commit('saveFilePid', payload)
        },
    }
})

export default store
