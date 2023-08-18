<template>
    <div style="width: 100%; height:100%;">
        <!-- 添加新建和上传等按钮 -->
        <div style="width: 100%; height:10%; background-color:blanchedalmond"></div>

        <!-- 显示文件 -->
        <div style="width: 100%; height:80%; ">
            <!-- 退回父目录 -->
            <div class="back-div" v-show="this.$store.getters.getCurrentDir !== ''">
                <div class="back-btn" @click="backToPreviousView">
                    <svg t="1691995311568" class="back-btn-icon" viewBox="0 0 1041 1024" version="1.1"
                        xmlns="http://www.w3.org/2000/svg" p-id="5351" width="30" height="30">
                        <path
                            d="M831.747371 420.007843c60.838623 36.268018 135.484717 134.100184 166.540014 251.862261 31.060413 117.76924 25.910113 223.224014 25.910113 223.224014s-40.209794-64.941058-56.512085-87.710644C951.382099 784.621051 882.686531 699.801247 776.058025 650.641617c-106.627483-49.158606-284.857746-40.404222-284.857746-40.404222l0 204.706265L0 467.010343l491.200278-347.934341 0 206.589149c0 0 117.998461 8.881274 179.679265 21.654182C774.766612 368.833323 831.747371 420.007843 831.747371 420.007843L831.747371 420.007843zM831.747371 420.007843"
                            fill="#8a8a8a" p-id="5352"></path>
                    </svg>
                </div>
            </div>
            <!-- 内容 -->
            <el-row :gutter="20">
                <el-col :span="8" v-for="colItem in  colItems " :key="colItem.id">
                    <button class="file-btn">
                        <!-- 目录 -->
                        <svg v-if="colItem.folderType == 1" t="1691739427959" class="dir" viewBox="0 0 1024 1024"
                            version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="18064" width="30" height="30">
                            <path
                                d="M855.04 385.024q19.456 2.048 38.912 10.24t33.792 23.04 21.504 37.376 2.048 54.272q-2.048 8.192-8.192 40.448t-14.336 74.24-18.432 86.528-19.456 76.288q-5.12 18.432-14.848 37.888t-25.088 35.328-36.864 26.112-51.2 10.24l-567.296 0q-21.504 0-44.544-9.216t-42.496-26.112-31.744-40.96-12.288-53.76l0-439.296q0-62.464 33.792-97.792t95.232-35.328l503.808 0q22.528 0 46.592 8.704t43.52 24.064 31.744 35.84 12.288 44.032l0 11.264-53.248 0q-40.96 0-95.744-0.512t-116.736-0.512-115.712-0.512-92.672-0.512l-47.104 0q-26.624 0-41.472 16.896t-23.04 44.544q-8.192 29.696-18.432 62.976t-18.432 61.952q-10.24 33.792-20.48 65.536-2.048 8.192-2.048 13.312 0 17.408 11.776 29.184t29.184 11.776q31.744 0 43.008-39.936l54.272-198.656q133.12 1.024 243.712 1.024l286.72 0z"
                                p-id="18065" fill="#8a8a8a"></path>
                        </svg>
                        <!-- 视频-->
                        <svg v-if="colItem.fileCategory === '0'" t="1691912832519" class="dir" viewBox="0 0 1024 1024"
                            version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6168" width="30" height="30">
                            <path
                                d="M960.43008 332.778496l-133.021696 45.024256L827.408384 260.871168c0-23.40352-18.967552-42.376192-42.372096-42.376192l-114.55488 0L253.991936 218.494976 73.79968 218.494976c-23.40352 0-42.376192 18.971648-42.376192 42.376192l0 349.394944 0 132.69504-0.556032 24.56064c0 23.399424 18.971648 42.376192 42.376192 42.376192L555.39712 809.897984l229.640192 0c23.40352 0 42.372096-18.975744 42.372096-42.376192l0-14.076928 0-66.9952 133.021696 45.02016c23.40352 0 42.376192-18.971648 42.376192-42.372096L1002.807296 375.154688C1002.805248 351.751168 983.8336 332.778496 960.43008 332.778496zM563.525632 531.45088 403.787776 651.986944c-3.692544 2.772992-8.1408 4.202496-12.58496 4.202496-3.182592 0-6.366208-0.735232-9.32352-2.2016-7.07584-3.509248-11.561984-10.77248-11.561984-18.683904L370.317312 401.570816c0-7.790592 4.365312-14.97088 11.298816-18.558976 6.892544-3.550208 15.276032-2.977792 21.6576 1.508352L563.01568 497.717248c5.44256 3.871744 8.70912 10.075136 8.809472 16.720896C571.925504 521.12896 568.84736 527.4112 563.525632 531.45088z"
                                fill="#FF9000" p-id="6169"></path>
                        </svg>
                        <!-- 音频 -->
                        <svg v-if="colItem.fileCategory === '1'" t="1691912905596" class="dir" viewBox="0 0 1024 1024"
                            version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1535" width="30" height="30">
                            <path
                                d="M481 511.4c49.4 0 89.5 32.1 89.5 71.6 0 39.5-40.2 71.6-89.5 71.6s-89.5-32.1-89.5-71.6c0-39.5 40.1-71.6 89.5-71.6m0-35.8c-69.2 0-125.3 48.1-125.3 107.4 0 59.3 56.1 107.4 125.3 107.4s125.3-48 125.3-107.4c0-59.3-56.1-107.4-125.3-107.4zM217.4 442.8c-0.8 0-1.5-0.1-2.3-0.3-4.8-1.2-7.6-6.1-6.4-10.9 1.4-5.2 2.9-10.3 4.5-15.4 1.5-4.7 6.5-7.3 11.2-5.8 4.7 1.5 7.3 6.5 5.8 11.2-1.5 4.8-2.9 9.6-4.2 14.5-1 4-4.6 6.7-8.6 6.7z m17.8-49.7c-1.3 0-2.5-0.3-3.7-0.8-4.5-2.1-6.5-7.4-4.4-11.9 30.4-65.9 83.1-119.6 148.5-151.2 4.5-2.2 9.8-0.3 12 4.2 2.2 4.5 0.3 9.8-4.2 12-61.6 29.8-111.4 80.5-140 142.6a9.16 9.16 0 0 1-8.2 5.1z"
                                fill="#FF7878" p-id="1536"></path>
                            <path
                                d="M511.8 959C265 959 64.2 758.2 64.2 511.4c0-232.7 174.5-424.3 406-445.7 13.7-1.3 27.7-1.9 41.6-1.9 9.9 0 17.9 8 17.9 17.9s-8 17.9-17.9 17.9c-12.8 0-25.7 0.6-38.3 1.7C260.6 121.1 100 297.3 100 511.4c0 227 184.7 411.8 411.8 411.8s411.8-184.7 411.8-411.8c0-63.6-14.1-124.5-41.9-181.2-8.9-18.2-19.3-35.9-30.9-52.6-5.6-8.1-3.6-19.3 4.6-24.9 8.1-5.6 19.3-3.6 24.9 4.6 12.5 18.1 23.8 37.4 33.5 57.2 30.2 61.6 45.6 127.9 45.6 196.9C959.3 758.2 758.6 959 511.8 959z m76.7-358.1c-9.9 0-17.9-8-17.9-17.9V128.4c0-27.9 14.6-47.9 38.1-52.2 0.3-0.1 6.8-1.5 13.1 0.1v-0.1c54.3 13 103.4 36.6 154.6 74.2 15.6 11.5 30.7 24.2 44.9 37.7 7.1 6.8 7.4 18.2 0.6 25.3s-18.2 7.4-25.3 0.6c-13-12.4-26.9-24.1-41.3-34.7-46.5-34.2-91-55.7-139.7-67.7h-1.1c-5.6 1.8-8.1 7.4-8.1 16.8V583c0 9.9-8.1 17.9-17.9 17.9z"
                                fill="#FF7878" p-id="1537"></path>
                        </svg>
                        <!-- 图片 -->
                        <img class="dir" style="width: 15%; height: 40px; margin-left: 17px;"
                            v-if="colItem.fileCategory === '2'" :src="url + 'uploadFile/' + colItem.filePath">
                        <!-- pdf -->
                        <svg v-if="colItem.fileCategory === '3'" t="1691935955004" class="dir" viewBox="0 0 1024 1024"
                            version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="12906" width="30" height="30">
                            <path
                                d="M534.78 74.67h57.71v91.61c108.81 0.61 217.73-1.12 326.43 0.51 23.41-2.24 41.74 16 39.39 39.39 1.73 190.15-0.41 380.39 1 570.64-1 20.56 2 43.36-9.77 61.58-14.86 10.78-34.2 9.36-51.6 10.18-101.79-0.51-203.58-0.31-305.47-0.31v101.8h-63.3C374 921.77 218.61 895.92 63.38 868.64q-0.15-356.22 0-712.33c157.07-27.18 314.13-54.87 471.4-81.64z"
                                fill="#DC2E1B" p-id="12907"></path>
                            <path
                                d="M110.51 551.6V427.19h37.32q21.22 0 27.65 1.87 9.9 2.8 16.57 12.18t6.68 24.23q0 11.45-3.85 19.25A33.46 33.46 0 0 1 185.1 497a32.41 32.41 0 0 1-12.1 5.88q-8.34 1.79-24.12 1.79h-15.12v46.93z m23.25-103.37v35.31h12.73q13.75 0 18.38-1.95a15.53 15.53 0 0 0 7.27-6.11 17.74 17.74 0 0 0 2.63-9.67 16.84 16.84 0 0 0-3.7-11.2 15.77 15.77 0 0 0-9.34-5.52q-4.18-0.85-16.73-0.85zM217.74 427.19h42.51q14.37 0 21.92 2.38A38.25 38.25 0 0 1 299.52 441a55.26 55.26 0 0 1 11 20.15q3.77 11.93 3.78 29.41 0 15.36-3.54 26.47-4.32 13.58-12.34 22-6 6.37-16.33 9.93-7.71 2.64-20.59 2.63h-43.76z m23.26 21v82.41h17.36q9.74 0 14.07-1.18a21 21 0 0 0 9.38-5.18q3.73-3.65 6.08-12t2.37-22.78q0-14.43-2.37-22.16t-6.59-12a21.61 21.61 0 0 0-10.77-5.85q-4.88-1.19-19.09-1.19zM334.16 551.6V427.19h78.95v21h-55.7v29.45h48.08v21h-48.08v52.96z"
                                fill="#FFFFFF" p-id="12908"></path>
                            <path
                                d="M815.32 596.59a22.61 22.61 0 0 0-3.14-2.81c-4.66-3.52-14.15-8.66-32.86-13.11q-2.79-0.67-5.77-1.29c12.42 9.27 23 15 31.7 17 5.21 1.3 8.38 0.8 10.07 0.21zM708.86 409c2.43-8.52 4.09-23.81 0.57-26.88l-0.06 0.07c-4.7 6.5-10.64 33.19-8.67 49.29 3.73-6.64 4.91-11.08 8.16-22.48zM709.47 552.2A211.78 211.78 0 0 1 683 499.62a541.34 541.34 0 0 1-45.11 54.52c16.99-1.68 43.93-2.64 71.58-1.94z"
                                fill="#FFFFFF" p-id="12909"></path>
                            <path
                                d="M589.1 196.82v361.91c9.41-2.69 15.9-4.05 15.9-4.05 5.64-5.88 5-5.33 11.77-12.6a758.94 758.94 0 0 0 59.94-73.54c-0.18-2.36-6.65-91.12 21.44-102.93a18.67 18.67 0 0 1 11.64-0.85c5.13 1.22 10 4.54 12.5 8.45 6.34 10.07 6.22 24.77 1 45.35-8.28 32.57-20.9 50.71-20.9 50.71s4 40.18 42 84.79c17.45 1.48 32.46 3.69 44.64 6.59 20.94 5 33.39 11.92 38.06 21.19 2.64 5.24 3 11.7-0.16 19.32s-6.37 11.4-12.2 13.64c-4.88 1.88-11 2-18.13 0.26-21.32-5.08-49.59-25.37-68-41.71-29.78-2.34-65.92-2.6-107.48-0.8q-18.53 19.91-32.07 31.85v213.35H925V196.82z"
                                fill="#FFFFFF" p-id="12910"></path>
                        </svg>
                        <!-- word文档 -->
                        <svg v-if="colItem.fileCategory === '4'" t="1691936014185" class="dir" viewBox="0 0 1024 1024"
                            version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1641" width="30" height="30">
                            <path
                                d="M959.16 187.68c2.14-23.23-16.1-41.26-39.32-38.92-108.92-1.76-217.84 0-326.84-0.61V66.64h-60.95C375.86 94.45 219.57 121 63.38 148.35v713c155.38 27.2 310.85 53.18 465.93 81.51H593v-81.51c102 0 203.87-0.2 305.86 0.31 17.32-0.82 36.58 0.51 51.45-10.09 11.92-18.24 8.87-41.06 9.89-61.64-1.45-200.72 0.69-401.53-1.04-602.25z"
                                fill="#2A5699" p-id="1642"></path>
                            <path
                                d="M593 178.71h336.21v652.08H593v-81.51h264.9v-40.76H593v-50.94h264.9v-40.76H593v-50.94h264.9v-40.75H593v-50.95h264.9v-40.75H593v-50.94h264.9v-40.76H593v-50.94h264.9V250H593zM115.57 419.49h42.54q14.39 0 21.94 2.38a38.18 38.18 0 0 1 17.38 11.46 55.43 55.43 0 0 1 11 20.18q3.78 11.94 3.78 29.43 0 15.37-3.54 26.51-4.33 13.58-12.35 22-6.05 6.37-16.35 9.94-7.71 2.64-20.6 2.64h-43.8z m23.28 21.06V523h17.38q9.75 0 14.07-1.18a21 21 0 0 0 9.39-5.18q3.74-3.66 6.09-12t2.36-22.81q0-14.43-2.36-22.17t-6.6-12.06a21.6 21.6 0 0 0-10.78-5.87q-4.87-1.18-19.1-1.18zM227.23 482.51q0-19 5.27-31.94a59.12 59.12 0 0 1 10.73-17.07 45.09 45.09 0 0 1 14.9-11.21q10.78-4.93 24.85-4.93 25.48 0 40.77 17.07T339 481.92q0 30.15-15.17 47.18t-40.58 17q-25.72 0-40.89-16.94t-15.13-46.65z m24-0.85q0 21.15 9 32.06a29.54 29.54 0 0 0 45.8 0.09Q315 503 315 481.32q0-21.4-8.69-31.94t-23.08-10.54q-14.39 0-23.19 10.67t-8.83 32.15zM351.31 503.49l22.69-2.38q2 12.31 8.29 18.09t16.82 5.8q11.25 0 16.94-5.14t5.7-12a11.94 11.94 0 0 0-2.4-7.51q-2.4-3.1-8.37-5.39-4.09-1.54-18.64-5.44-18.72-5-26.26-12.32a33.55 33.55 0 0 1-10.62-25.05 33.91 33.91 0 0 1 5-17.8 31.92 31.92 0 0 1 14.39-12.61q9.4-4.34 22.69-4.34 21.7 0 32.67 10.28t11.52 27.44l-23.28 1.1q-1.49-9.6-6.41-13.8t-14.74-4.21q-10.15 0-15.88 4.5a9.33 9.33 0 0 0-3.7 7.73 9.85 9.85 0 0 0 3.46 7.56q4.4 4 21.39 8.33t25.12 9a33.6 33.6 0 0 1 12.74 12.66q4.6 8 4.6 19.84a38.79 38.79 0 0 1-5.51 20A33.64 33.64 0 0 1 424 541.67q-10.07 4.55-25.08 4.55-21.86 0-33.58-10.91t-14.03-31.82z"
                                fill="#FFFFFF" p-id="1643"></path>
                        </svg>
                        <!-- excel -->
                        <svg v-if="colItem.fileCategory === '5'" t="1691936045088" class="dir" viewBox="0 0 1024 1024"
                            version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1786" width="30" height="30">
                            <path
                                d="M533.58 75H594v81.31c101.74 0 203.47 0.2 305.21-0.31 17.18 0.71 36.09-0.51 51 9.76 10.47 15 9.25 34.15 10 51.43q-0.76 264.66-0.2 529.12c-0.51 29.58 2.74 59.76-3.45 88.93-4.14 21.1-29.56 21.61-46.56 22.32-105.3 0.31-210.69-0.2-316.09 0V949h-63.07c-154.9-28.15-310.1-54.17-465.2-81.31V156.37C221.66 129.23 377.67 102.51 533.58 75z"
                                fill="#207245" p-id="1787"></path>
                            <path
                                d="M594 186.76h335.4v640.31H594v-61h81.31v-71.12H594v-40.66h81.31v-71.14H594v-40.66h81.31v-71.14H594v-40.66h81.31v-71.14H594v-40.66h81.31v-71.15H594z"
                                fill="#FFFFFF" p-id="1788"></path>
                            <path
                                d="M715.92 247.74h142.29v71.15H715.92zM715.92 359.55h142.29v71.15H715.92zM715.92 471.35h142.29v71.15H715.92zM715.92 583.15h142.29v71.15H715.92zM715.92 694.95h142.29v71.15H715.92z"
                                fill="#207245" p-id="1789"></path>
                            <path
                                d="M98.48 551.16l39.3-64.82-35.61-59.39h27.14l23.06 39.91L175 426.95h26.91l-35.77 60.33 39.3 63.89h-28l-25.49-43-25.57 43zM217.94 551.16V428h23.22v102.23h57.73v20.93zM309.56 510.75l22.59-2.37q2 12.28 8.27 18t16.82 5.77q11.22 0 16.9-5.13t5.69-12a11.91 11.91 0 0 0-2.39-7.49q-2.39-3.1-8.35-5.38-4.08-1.53-18.59-5.43-18.67-5-26.2-12.29a33.47 33.47 0 0 1-10.59-25 33.82 33.82 0 0 1 5-17.76A31.84 31.84 0 0 1 333 429.15q9.37-4.33 22.63-4.33 21.65 0 32.59 10.25t11.49 27.37l-23.22 1.1q-1.49-9.57-6.39-13.77t-14.7-4.2q-10.12 0-15.85 4.49a9.3 9.3 0 0 0-3.69 7.71 9.83 9.83 0 0 0 3.45 7.54q4.39 4 21.33 8.31t25.06 8.93a33.52 33.52 0 0 1 12.7 12.63q4.59 8 4.59 19.79a38.7 38.7 0 0 1-5.49 20 33.56 33.56 0 0 1-15.5 13.86q-10 4.54-25 4.54-21.81 0-33.49-10.89t-13.95-31.73z"
                                fill="#FFFFFF" p-id="1790"></path>
                        </svg>
                        <!-- txt -->
                        <svg v-if="colItem.fileCategory === '6'" t="1691918416144" class="dir" viewBox="0 0 1024 1024"
                            version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6166" width="30" height="30">
                            <path
                                d="M535.94 71.69h57.58v91.4c108.56 0.61 217.22-1.12 325.68 0.51 23.36-2.23 41.64 15.95 39.3 39.3 1.73 189.71-0.41 379.52 1 569.33-1 20.51 2 43.26-9.75 61.44-14.83 10.76-34.12 9.34-51.48 10.16-101.56-0.51-203.11-0.31-304.77-0.31v101.55h-63.15C375.59 916.83 220.5 891 65.63 863.82q-0.15-355.4 0-710.69C222.34 126 379 98.39 535.94 71.69z"
                                fill="#868695" p-id="6167"></path>
                            <path
                                d="M138.52 547.51V444.39h-34.1v-21h91.31v21h-34v103.12zM199 547.51l39.27-64.77-35.58-59.35h27.12l23 39.88 22.57-39.88h26.89l-35.74 60.28 39.27 63.84h-28l-25.4-42.92-25.55 42.92zM343.63 547.51V444.39h-34.1v-21h91.31v21h-34v103.12zM590.14 193.55v302.14h126.94v12.69H590.14v88.86h265.74v38.08H590.14v48.24h265.74v38.08H590.14V813h335.14V193.55z m198.11 250c-4.53 6.7-10.62 17.86-12.12 31.13a8.11 8.11 0 0 1-5.17 6.88l-43.83 15.86-4.93-2.57 20.22-27.09a15.45 15.45 0 0 0 5 0.79c7.84-0.08 14.14-5.95 14.07-13.09s-6.49-12.89-14.33-12.81-14.14 5.94-14.06 13.09a11.89 11.89 0 0 0 1 4.63 8.05 8.05 0 0 1-0.55 8.14l-17.15 23-4.09-3.82-0.13-42.48a8.25 8.25 0 0 1 4.66-7.51c6.93-3.32 20.19-10.74 30.28-22.81a8.22 8.22 0 0 1 10.81-1.46l28 18.43a8.35 8.35 0 0 1 2.32 11.72z m19.94-14.67a8.35 8.35 0 0 1-11 2l-42.74-26.63a8.35 8.35 0 0 1-2.52-11.73l0.32-0.47c2.53-3.77 4.47-1.64 8.3 0.8L802.93 420c4.13 2.61 8.25 5 5.25 8.91z m36.18-53.69l-24.11 35.7a13 13 0 0 1-18 3.48l-28.55-19.27a13 13 0 0 1-3.48-18l24.11-35.69a12.94 12.94 0 0 1 18-3.48l28.56 19.29a13 13 0 0 1 3.46 18z"
                                fill="#FFFFFF" p-id="6168"></path>
                        </svg>
                        <!-- code -->
                        <svg v-if="colItem.fileCategory === '7'" t="1691918486942" class="dir" viewBox="0 0 1024 1024"
                            version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="9180" width="30" height="30">
                            <path
                                d="M981.333333 276.053333V981.333333a42.666667 42.666667 0 0 1-42.666666 42.666667H85.333333a42.666667 42.666667 0 0 1-42.666666-42.666667V42.666667a42.666667 42.666667 0 0 1 42.666666-42.666667h619.946667z"
                                fill="#B9C9D5" p-id="9181"></path>
                            <path
                                d="M705.28 233.386667V0L981.333333 276.053333H747.946667a42.666667 42.666667 0 0 1-42.666667-42.666666z"
                                fill="#D6E4F0" p-id="9182"></path>
                            <path
                                d="M357.153827 828.153509m-18.856181-18.856181l-143.306974-143.306974q-18.856181-18.856181 0-37.712362l0 0q18.856181-18.856181 37.712361 0l143.306975 143.306975q18.856181 18.856181 0 37.712361l0 0q-18.856181 18.856181-37.712362 0Z"
                                fill="#FFFFFF" p-id="9183"></path>
                            <path
                                d="M176.168321 647.240833m18.856181-18.85618l143.306974-143.306975q18.856181-18.856181 37.712362 0l0 0q18.856181 18.856181 0 37.712362l-143.306974 143.306974q-18.856181 18.856181-37.712362 0l0 0q-18.856181-18.856181 0-37.712361Z"
                                fill="#FFFFFF" p-id="9184"></path>
                            <path
                                d="M666.875271 466.282909m18.856181 18.856181l143.306974 143.306974q18.856181 18.856181 0 37.712362l0 0q-18.856181 18.856181-37.712362 0l-143.306974-143.306974q-18.856181-18.856181 0-37.712362l0 0q18.856181-18.856181 37.712362 0Z"
                                fill="#FFFFFF" p-id="9185"></path>
                            <path
                                d="M847.807757 647.200761m-18.85618 18.856181l-143.306975 143.306974q-18.856181 18.856181-37.712361 0l0 0q-18.856181-18.856181 0-37.712361l143.306974-143.306975q18.856181-18.856181 37.712362 0l0 0q18.856181 18.856181 0 37.712362Z"
                                fill="#FFFFFF" p-id="9186"></path>
                            <path
                                d="M441.216861 805.202833m6.901841-25.758022l74.539885-278.186638q6.901841-25.758022 32.659863-18.856181l0 0q25.758022 6.901841 18.856181 32.659864l-74.539885 278.186638q-6.901841 25.758022-32.659863 18.85618l0 0q-25.758022-6.901841-18.856181-32.659863Z"
                                fill="#FFFFFF" p-id="9187"></path>
                        </svg>
                        <!-- 压缩包 -->
                        <svg v-if="colItem.fileCategory === '8'" t="1691936092018" class="dir" viewBox="0 0 1024 1024"
                            version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1933" width="30" height="30">
                            <path
                                d="M714.48 782.13a22.4 22.4 0 0 0 3.09-12.21q0-8.22-4.06-13.27a18.26 18.26 0 0 0-11.46-6.38q-3.7-0.55-22.19-0.55h-23.74V791h22.5q21.89 0 27.33-2a16.51 16.51 0 0 0 8.53-6.87zM512.4 820.54h41.1l-20.75-60.38-20.35 60.38z"
                                fill="#FFB233" p-id="1934"></path>
                            <path
                                d="M902.56 82.78H121.3a49.58 49.58 0 0 0-49.58 49.58v761.26a49.58 49.58 0 0 0 49.58 49.58h781.26a49.58 49.58 0 0 0 49.58-49.58V132.37a49.58 49.58 0 0 0-49.58-49.59z m-391.12 59.54H552v40.59h-40.56z m0 81.19H552v40.59h-40.56z m0 81.19H552v40.59h-40.56z m0 81.2H552v40.59h-40.56z m-40.59-284.17h40.59v40.59h-40.59z m0 81.2h40.59v40.59h-40.59z m0 81.18h40.59v40.59h-40.59z m0 81.2h40.59v40.59h-40.59z m0 81.18h40.59v40.59h-40.59z m-8.59 64.15h98.84V621h-98.84z m-41.39 394.27l-21.95-35.41q-11.71-19-16-23.92a23.44 23.44 0 0 0-9.14-6.76q-4.85-1.82-15.33-1.82h-6.16v67.9H321.8V722.22h64q24.16 0 35.09 4.38t17.46 15.58a49.71 49.71 0 0 1 6.58 25.64q0 18.31-10 30.26t-29.8 15a72.09 72.09 0 0 1 16.29 13.66q6.42 7.43 17.31 26.41l18.38 31.74z m155.53 0l-13.14-37H503l-12.41 37h-32.24L517 722.22h32.16l60.3 162.69z m148.39 0l-22-35.41q-11.71-19-16-24a23.44 23.44 0 0 0-9.14-6.76q-4.79-1.74-15.27-1.74h-6.21v67.9H625.7V722.22h64q24.16 0 35.09 4.38t17.52 15.61a49.71 49.71 0 0 1 6.58 25.64q0 18.31-10 30.26t-29.8 15a72.09 72.09 0 0 1 16.29 13.66q6.42 7.43 17.31 26.41l18.39 31.74z"
                                fill="#FFB233" p-id="1935"></path>
                            <path
                                d="M410.57 782.13a22.4 22.4 0 0 0 3.09-12.21q0-8.22-4.06-13.27a18.26 18.26 0 0 0-11.46-6.38q-3.7-0.55-22.19-0.55h-23.74V791h22.48q21.89 0 27.33-2a16.51 16.51 0 0 0 8.55-6.87z"
                                fill="#FFB233" p-id="1936"></path>
                            <path
                                d="M470.85 101.73h40.59v40.59h-40.59zM470.85 182.93h40.59v40.59h-40.59zM511.44 142.32h40.59v40.59h-40.59z"
                                fill="#FFFFFF" p-id="1937"></path>
                            <path d="M511.44 223.51h40.59v40.59h-40.59zM470.85 264.1h40.59v40.59h-40.59z" fill="#FFFFFF"
                                p-id="1938"></path>
                            <path
                                d="M511.44 304.69h40.59v40.59h-40.59zM470.85 345.3h40.59v40.59h-40.59zM511.44 385.89h40.59v40.59h-40.59z"
                                fill="#FFFFFF" p-id="1939"></path>
                            <path
                                d="M470.85 426.47h40.59v40.59h-40.59zM462.26 490.62h98.83v130.35h-98.83zM421.45 826.76a72.09 72.09 0 0 0-16.29-13.66Q425 810 435 798.07t10-30.26a49.71 49.71 0 0 0-6.58-25.64q-6.53-11.2-17.46-15.58t-35.09-4.38h-64V884.9h30.49V817h6.16q10.48 0 15.31 1.83a23.44 23.44 0 0 1 9.14 6.76q4.31 4.94 16 23.92l21.95 35.42h36.27l-18.38-31.74q-10.94-19-17.36-26.43zM374.72 791h-22.48v-41.28h23.73q18.5 0 22.19 0.55a18.26 18.26 0 0 1 11.46 6.38q4.06 5 4.06 13.27a22.4 22.4 0 0 1-3.09 12.21A16.51 16.51 0 0 1 402 789q-5.39 2-27.28 2zM517 722.22L458.35 884.9h32.27l12.41-37h60.22l13.14 37h33.08l-60.3-162.69z m-4.61 98.32l20.35-60.38 20.75 60.38zM725.41 826.79a72.09 72.09 0 0 0-16.29-13.66q19.82-3.09 29.8-15t10-30.26a49.71 49.71 0 0 0-6.58-25.64q-6.61-11.23-17.54-15.64t-35.09-4.38h-64V884.9h30.47V817h6.21q10.48 0 15.31 1.83a23.44 23.44 0 0 1 9.14 6.76q4.26 4.94 16 24l22 35.41h36.32l-18.44-31.8q-10.89-18.98-17.31-26.41zM678.63 791h-22.5v-41.28h23.73q18.5 0 22.19 0.55a18.26 18.26 0 0 1 11.46 6.38q4.06 5 4.06 13.27a22.4 22.4 0 0 1-3.09 12.21A16.51 16.51 0 0 1 706 789q-5.48 2-27.37 2z"
                                fill="#FFFFFF" p-id="1940"></path>
                        </svg>
                        <!-- 其他 -->
                        <svg v-if="colItem.fileCategory === '9'" t="1691931311277" class="dir" viewBox="0 0 1025 1024"
                            version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="11578" width="30" height="30">
                            <path
                                d="M525.354 79.891c25.407 0 46.004 20.583 46.004 45.974v204.803c0 19.043 15.447 34.481 34.503 34.481h218.694c25.407 0 46.003 20.583 46.003 45.974v420.448c0 69.825-56.64 126.429-126.51 126.429H249.51C179.64 958 123 901.396 123 831.57V206.32c0-69.825 56.64-126.429 126.51-126.429h275.844zM492.05 751.116c-9.546 0-18.137 2.86-24.82 9.536-6.682 6.198-10.023 14.303-10.023 24.316 0 9.536 3.341 17.641 10.024 24.317 6.682 6.675 15.273 10.012 24.82 10.012 9.545 0 18.137-3.337 24.819-9.536 6.682-6.675 10.5-14.78 10.5-24.793s-3.34-18.118-10.023-24.316c-6.682-6.676-15.273-9.536-25.297-9.536z m8.592-310.327c-35.32 0-63.004 10.013-83.05 30.515-18.955 18.935-28.949 44.263-29.981 76.39a60.36 60.36 0 0 0-0.019 1.33c0 13.8 11.188 24.987 24.988 24.987 13.783 0 24.999-11.094 25.149-24.877 0.01-0.955 0.021-1.571 0.033-1.848 0.759-18.082 5.013-32.284 12.763-42.606 9.546-13.827 25.297-20.503 46.776-20.503 17.182 0 31.024 4.768 40.57 14.304 9.069 9.536 13.842 22.41 13.842 39.097 0 12.397-4.773 23.84-13.365 34.806l-8.114 9.06c-29.592 26.223-47.73 45.772-53.935 59.122-6.682 12.396-9.546 27.654-9.546 45.295v14.587c0 14.103 11.433 25.536 25.536 25.536 14.103 0 25.535-11.433 25.535-25.536v-14.587c0-11.443 2.387-21.932 7.637-31.468 4.296-8.582 10.978-17.165 20.047-24.793 22.433-19.549 35.797-31.946 40.093-37.19 11.932-15.258 18.137-34.806 18.137-58.17 0-28.607-9.546-51.493-28.638-68.18-19.092-17.165-43.911-25.27-74.458-25.27zM619.837 76.995a18.4 18.4 0 0 1 12.725 5.109l234.743 224.735c7.34 7.028 7.595 18.675 0.567 26.016a18.4 18.4 0 0 1-13.291 5.675H633.637c-17.783 0-32.2-14.416-32.2-32.2V95.395c0-10.162 8.238-18.4 18.4-18.4z"
                                fill="#8a8a8a" p-id="11579"></path>
                        </svg>
                        <!-- 文件 -->
                        <div class="dir-content" v-on:dblclick="subFiles(colItem)">
                            {{ colItem.fileName }}
                        </div>

                        <!-- 弹窗 -->
                        <el-popover popper-class="operation-dialog" :visible-arrow="false" placement="bottom" width="150"
                            trigger="hover">
                            <div class="dialog-div">
                                <!-- 下载 -->

                                <button class="download-btn" @click="downloadFile(colItem)">
                                    下载
                                </button>
                                <!-- 重命名 -->
                                <button class="resetname-btn" @click="resetFileName(colItem)">
                                    重命名
                                </button>
                                <!-- 删除 -->
                                <button class="delete-btn" @click="deleteFile(colItem)">
                                    删除
                                </button>
                                <!-- 详细信息 -->
                                <button class="detail-btn" @click="fileDetails(colItem)">
                                    详细信息
                                </button>
                            </div>
                            <!-- 菜单按钮 -->
                            <button class="dir-btn" slot="reference">
                                <i class="el-icon-s-operation"></i>
                            </button>
                        </el-popover>
                    </button>

                </el-col>
            </el-row>

        </div>
        <!-- 分页 -->
        <div class="pagination-div" style="height: 10%;">
            <el-pagination hide-on-single-page :current-page="currentPage" :page-size="pageSize" layout="prev, pager, next"
                :total="totalNum" @current-change="getAllFiles">
            </el-pagination>
        </div>
    </div>
</template>

<script scoped>
export default {
    inject: ['reload'],
    components: {},
    props: {},
    data() {
        return {
            //数组大小=16
            colItems: [],   //存储子文件
            email: '',
            currentPage: 1,
            currentDir: '',
            token: '',
            //分页
            pageSize: 12,
            totalNum: 0,   //总共页数
            // 过渡动画
            show: true,
            url: '',
        };
    },
    // 渲染试图前
    created() {
        this.email = localStorage.getItem('email');  //获取邮箱
        this.token = localStorage.getItem('token');   //获取token
        this.url = this.$global.Url;
        //初始化当前目录下所有文件
        this.getAllFiles();
    },
    methods: {

        /**
         * 下载文件
         * @param {文件属性} colItem 
        */
        downloadFile(colItem) {
            let param = {
                colItem: colItem
            }
            this.$axios.post('index/download', param, {
                responseType: 'blob',    //响应文件流为二进制
                headers: { token: this.token }
            }).then(value => {
                console.log(value);
                if (colItem.folderType == 1) {  //文件夹
                    if (value.status == 200) {
                        var blob = new Blob([value.data]);     //响应的二进制文件流
                        // console.log(value.data);
                        const url = window.URL.createObjectURL(blob);
                        const link = document.createElement('a');
                        link.href = url;
                        link.setAttribute('download', colItem.fileName + '.zip');
                        document.body.appendChild(link);
                        link.click();
                        // 释放URL 对象
                        URL.revokeObjectURL(link.href);
                        document.body.removeChild(link);
                    }
                } else if (colItem.folderType == 0) {   //下载文件
                    var blob = new Blob([value.data]);
                    // console.log(value.data);
                    const url = window.URL.createObjectURL(blob);
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', colItem.fileName);
                    document.body.appendChild(link);
                    link.click();
                    // 释放URL 对象
                    URL.revokeObjectURL(link.href);
                    document.body.removeChild(link);
                } else {
                    this.$notify({
                        title: '发生一些错误,请联系管理员',
                        position: 'top-right',  //显示位置
                        duration: 3000,  // 3秒关闭
                        type: 'error',
                        offset: 80
                    });
                }

            }).catch(() => {
                this.$notify({
                    title: '发生一些错误,请联系管理员',
                    position: 'top-right',  //显示位置
                    duration: 3000,  // 3秒关闭
                    type: 'error',
                    offset: 80
                });
            });
        },

        /**
         * 文件重命名
         */
        resetFileName(colItem) {
            this.$prompt('请输入文件名称', '文件重命名', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(({ value }) => {
                let param = {
                    newFileName: value,
                    colItem, colItem
                }
                this.$axios.post('index/resetFileName', param, {
                    headers: { token: this.token }
                }).then(value => {
                    // console.log(value.data);
                    if (value.data.status === '0') {
                        this.$notify({
                            title: '文件重命名成功',
                            position: 'top-right',  //显示位置
                            duration: 3000,  // 3秒关闭
                            type: 'success',
                            offset: 80
                        });
                        //刷新页面
                        this.reload();
                    } else {
                        this.$notify({
                            title: value.data.message,
                            position: 'top-right',  //显示位置
                            duration: 3000,  // 3秒关闭
                            type: 'error',
                            offset: 80
                        });
                    }
                }).catch(() => {
                    this.$notify({
                        title: '发生一些错误,请联系管理员',
                        position: 'top-right',  //显示位置
                        duration: 3000,  // 3秒关闭
                        type: 'error',
                        offset: 80
                    });
                });
            }).catch(() => { });
        },
        /**
         * 查询文件详细信息
         * 通过vuex状态管理，将数据传回父路由
         * @param {文件详细信息} colItem 
         */
        fileDetails(colItem) {
            this.$store.commit('saveColItem', colItem);
            this.$store.commit('changeShowDetail', true);
        },
        /**
         * 删除文件
         */
        deleteFile(colItem) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true,
            }).then(() => {
                //发送删除请求
                this.$axios.post('index/delete', colItem, {
                    headers: { token: this.token }
                }).then(value => {
                    console.log(value.data);
                    if (value.data.status === '0') {
                        this.$notify({
                            title: '文件删除成功',
                            position: 'top-right',  //显示位置
                            duration: 3000,  // 3秒关闭
                            type: 'success',
                            offset: 80
                        });
                        //刷新页面
                        this.reload();
                    }
                })
            }).catch(() => { });
        },
        /**
         * 退回上一个目录
         */
        backToPreviousView() {
            //获取全路径和当前目录
            let absolutePath = this.$store.getters.getAbsolutePath;
            let currentDir = this.$store.getters.getCurrentDir;
            //退回时修改的全路径和当前目录
            let modifiedAbsolutePath = absolutePath.substring(0, absolutePath.lastIndexOf('/'));
            let partsCurrentDir = absolutePath.replace("/" + currentDir, "").split('/');
            let modifiedCurrentDir = partsCurrentDir[partsCurrentDir.length - 1];
            //修改全路径和当前目录到vuex状态管理
            this.$store.commit('saveCurrentDir', modifiedCurrentDir);
            this.$store.commit('modifiedAbsolutePath', modifiedAbsolutePath);
            //获取所有子文件
            this.getAllFiles();
        },
        /**
        * 双击显示所有子文件
        */
        subFiles(colItem) {
            //判断点击的是否是文件夹
            if (colItem.folderType != 1) {  //文件
                return;
            } else {
                // console.log(colItem);
                //将当前目录存储到vuex状态管理
                this.$store.commit('saveCurrentDir', colItem.fileName);
                this.$store.commit('saveAbsolutePath', colItem.fileName);
                this.getAllFiles();
            }
        },
        // 跳转到最近使用界面
        /**
         * 获取当前目录下所有文件
         */
        getAllFiles(newPage) {
            if (newPage) {
                this.currentPage = newPage;
            }
            let param = {
                email: this.email,
                currentDir: this.$store.getters.getCurrentDir,
                absolutePath: this.$store.getters.getAbsolutePath,
                currentPage: this.currentPage,
                pageSize: this.pageSize
            };
            // console.log(param);
            this.$axios.post('index/allFiles', param, {
                headers: { token: this.token }
            }).then(value => {
                // console.log(value.data);
                this.totalNum = value.data.data.totalNum;
                this.colItems = value.data.data.fileList;
                // console.log(this.colItems);
            }).catch(() => {
                this.$notify({
                    title: '发生一些错误，请联系管理员!',
                    position: 'top-right',  //显示位置
                    duration: 3000,  // 2秒关闭
                    type: 'error',
                    offset: 80
                });
            });
        },
    },
    // 渲染试图后
    mounted() {

    },
};
</script>


<style lang="scss" scoped>
.newDir-dialog {
    .newDir-btn-input {
        border: 2px, solid #edf2fc;
        height: 40px;
        width: 100%;
        outline: none;
    }

    .dialog-footer {
        display: flex;
        align-items: center;
        justify-content: right;

        .newDir-btn-cancel {
            font-size: small;
            background-color: white;
            border: none;
            width: 70px;
            height: 40px;
            margin-right: 40px;
            border-radius: 10px;
        }

        .newDir-btn-cancel:hover {
            background-color: #edf2fc;
            cursor: pointer;
            // filter: brightness(0.9);
        }

        .newDir-btn-cancel:active {
            transform: scale(0.95);
        }

        .newDir-btn-ok {
            font-size: small;
            background-color: white;
            border: none;
            width: 70px;
            height: 40px;
            border-radius: 10px;
            margin-right: 10px;
        }

        .newDir-btn-ok:hover {
            background-color: #edf2fc;
            cursor: pointer;
            // filter: brightness(0.9);
        }

        .newDir-btn-ok:active {
            transform: scale(0.95);
        }
    }

}

.back-div {
    width: 100%;
    height: 30px;

    .back-btn {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 5%;
        height: 100%;
        border-radius: 20px;
        margin-left: 19px;
        background-color: #f2f6fc;
    }

    .back-btn:hover {
        cursor: pointer;
        filter: brightness(0.95);
    }

    .back-btn:active {
        transform: scale(0.95);
    }

}

.pagination-div {
    display: flex;
    align-items: center;
    justify-content: center;

    // 左右箭头
    /deep/ .el-pagination {

        button {
            border: none;
            border-radius: 20px;
        }

        .el-pager {

            // 页数按钮
            li {
                margin-right: 5px;
                margin-left: 5px;
                border: none;
                border-radius: 20px;
                background-color: #edf2fc;
            }

            .active {
                color: black;
                filter: brightness(0.9);
            }

            li:hover {
                filter: brightness(0.9);
            }

            li:not(.active):hover {
                color: black !important;
            }
        }

    }


}

.el-row {
    height: 15%;

    .el-col {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
        margin-top: 30px;
        border-radius: 20px;

        .file-btn {
            font-size: 16px;
            display: flex;
            // 垂直居中
            align-items: center;
            // 水平位置
            justify-content: left;
            border: none;
            width: 90%;
            height: 80%;
            background-color: #f2f6fc;
            border-radius: 15px;

            .dir {
                width: 20%;
            }

            .dir-content {
                display: flex;
                // 垂直居中
                align-items: center;
                height: 100%;
                width: 60%;
                margin-left: 20px;
                // 内容过长
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }



            .dir-btn {
                display: flex;
                // 垂直居中
                align-items: center;
                // 水平位置
                justify-content: center;
                width: 30px;
                height: 32px;
                border-radius: 50px;
                border: none;
                background-color: #f2f6fc;

                .el-icon-s-operation {
                    display: flex;
                    // 垂直居中
                    align-items: center;
                    justify-content: center;
                    width: 100%;
                    height: 40px;
                    border-radius: 50px;
                }

            }

            .dir-btn:hover {
                cursor: pointer;
                background-color: #d3d9e3;
            }

            .dir-btn:active {
                transform: scale(0.95);
            }

        }

        .file-btn:hover {
            filter: brightness(0.95);
        }

        .file-btn:active {
            filter: brightness(0.95);
        }

    }
}
</style>

<style lang="scss">
.operation-dialog {
    display: flex;
    // 垂直居中
    align-items: center;
    background-color: #edf2fc;
    border-radius: 20px;
    height: 150px;

    .dialog-div {
        width: 100%;
        height: 100%;
        background-color: #f7f9fc;
        border-radius: 20px;

        .download-btn {
            border: none;
            border-top-left-radius: 20px;
            border-top-right-radius: 20px;
            background-color: white;
            height: 25%;
            width: 100%;
        }

        .download-btn:hover {
            background-color: #e2e9fa;
            cursor: pointer;
        }

        .download-btn:active {
            transform: scale(0.95);
        }

        .resetname-btn {
            border: none;
            background-color: white;
            height: 25%;
            width: 100%;
        }

        .resetname-btn:hover {
            background-color: #e2e9fa;
            cursor: pointer;
        }

        .resetname-btn:active {
            transform: scale(0.95);
        }

        .delete-btn {
            border: none;
            background-color: white;
            height: 25%;
            width: 100%;

            //删除弹窗
            .delete-dialog .el-button--primary {
                background-color: #ff0000;
                /* 自定义主按钮的背景色 */
            }


        }

        .delete-btn:hover {
            background-color: #e2e9fa;
            cursor: pointer;
        }

        .delete-btn:active {
            transform: scale(0.95);
        }

        .detail-btn {
            border: none;
            border-bottom-left-radius: 20px;
            border-bottom-right-radius: 20px;
            background-color: white;
            height: 25%;
            width: 100%;
        }

        .detail-btn:hover {
            background-color: #e2e9fa;
            cursor: pointer;
        }

        .detail-btn:active {
            transform: scale(0.95);
        }
    }

}
</style>