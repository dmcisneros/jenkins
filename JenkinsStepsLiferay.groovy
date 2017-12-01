node{
    stage 'Stage_Update_Repository'
        echo 'Task_Update_Repository'
        //build job:'Task_Update_Repository'
        //wITH PARAMETERS: build job:'Task_Update_Repository',parameters:[[$class:'StringParameterValue',name:'URL_GIT',value:URL_GIT]]
    stage 'Stage_Test'
        parallel qa:{
                if(SKIP_QA.equals("false")){
                    echo '********Task_QA'
                    build job:'Task_QA'
                }
            }, unitTest:{
                if(SKIP_UNIT_TESTS.equals("false")){
                    echo '********Task_Unit_Tests'
                    build job:'Task_Unit_Tests'
                }    
            }
    stage 'Stage_Compilation'
        parallel modules:{
                echo 'Begin Task_Build_Modules'
                build job:'Task_Build_Modules',parameters:[[$class:'StringParameterValue',name:'BUILD_ENVIRONMENT',value:BUILD_ENVIRONMENT]]
                echo 'End Task_Build_Modules'
            }, themes:{
                echo 'Starting Task_Build_Themes'
                build job:'Task_Build_Themes',parameters:[[$class:'StringParameterValue',name:'BUILD_ENVIRONMENT',value:BUILD_ENVIRONMENT]]
                echo 'End Task_Build_Themes'
            }, layouts:{
                echo 'Starting Task_Build_Layouts'
                build job:'Task_Build_Layouts',parameters:[[$class:'StringParameterValue',name:'BUILD_ENVIRONMENT',value:BUILD_ENVIRONMENT]]
                echo 'End Task_Build_Layouts'
            }
}