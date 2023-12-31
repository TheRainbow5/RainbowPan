// 导入 vue和vuex,并把vuex挂载到vue实例
import Vue from 'vue'
import Vuex from 'vuex'


Vue.use(Vuex)

const store = new Vuex.Store({
    // 存储全局变量
    state: {
        currentDir: '',   //默认目录为用户的根目录
        absolutePath: '',   //全路径
        colItem: undefined,
        showDetail: false,
        searchInput: '',
    },
    // 修改全局变量必须通过mutations中的方法
    mutations: {
        /**
         * 保存搜索条件
         */
        saveSearchInput(state, payload) {
            state.searchInput = payload;
        },
        /**
         * 存储当前目录
         * @param {*} state 
         * @param {传入的参数} payload 
         */
        saveCurrentDir(state, payload) {
            state.currentDir = payload;
            // console.log("当前目录：" + state.currentDir);
        },
        saveAbsolutePath(state, payload) {
            if (payload == '') {
                state.absolutePath = payload;
            } else {
                state.absolutePath += "/" + payload;
            }
            // console.log("当前全路径：" + state.absolutePath);
        },
        //退回的全路径
        modifiedAbsolutePath(state, payload) {
            state.absolutePath = payload;
            // console.log("当前全路径：" + state.absolutePath);
        },
        //
        saveColItem(state, payload) {
            state.colItem = payload;
            // console.log(state.colItem);
        },
        changeShowDetail(state, payload) {
            state.showDetail = payload;
        },
    },
    // 获取属性值
    getters: {
        getSearchInput(state) {
            return state.searchInput;
        },
        // 返回当前目录
        getCurrentDir: function (state) {
            return state.currentDir;
        },
        // 返回全路径
        getAbsolutePath: function (state) {
            return state.absolutePath;
        },
        getColItem: function (state) {
            return state.colItem;
        },
        getShowDetail(state) {
            return state.showDetail;
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
