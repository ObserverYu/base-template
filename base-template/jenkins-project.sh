#!/bin/bash

backup_path=backup/
project_git_url=""
project_path=/mnt/project/electric
backup_path=/mnt/beifen/
gitpull(){
  cd $project_path
  git pull -v origin sanqi:sanqi
  echo "从gitee拉取electric-parent最新代码成功"
}
build(){
	mvn install  -Dmaven.multiModuleProjectDirectory=$project_path   -am -DskipTests=true install
	echo "项目构建完成";
}

echoError(){
	echo "$1 命令不存在"
	exit
}

copy(){
	jar_name=$2.jar
	run_path=$base_path/$2
	run_file=$run_path/$jar_name
	from=$project_path/$2/target/$jar_name;
	if [ ! -f "$from" ];then
      echo "模块不存在 部署中断";
	  exit
	fi
	
	#echo $target_path
	if [ ! -d "$run_path" ];then
		mkdir -p $run_path
		echo "$run_path 创建成功 "
	fi
	#考虑版本号的问题 或者索性就去掉版本号
	
	#如果已经存在就备份一下
	if [ -f "$run_file" ];then
       mv $run_file  $backup_path/$jar_name.`date "+%Y-%m-%d_%H:%M"`
	   echo "备份完成"
	fi

	cp -r $from $run_path

}
stop(){
	pid=`ps -ef | grep $2.jar | grep -v grep| awk '{print $2}'`
	if [ -n "$pid" ];then
		echo "kill -9 的 pid:"$pid
		kill -9 $pid
	fi
	echo "清除进程完成"
}
run(){
    #aaaaa
    echo "配置文件为:$3"
    echo "jar包路径:/home/work/$2/target/$2.jar"
    echo "nohup重定向:/home/work/$2/$2-nohup.out"
    echo "jmx远程端口:$5"
    echo "本机host:$4"
    echo "完整命令: nohup java -jar -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$5 \
                   -Dcom.sun.management.jmxremote.ssl=false \
                   -Dcom.sun.management.jmxremote.authenticate=false \
                   -Dspring.profiles.active=$3 \
                   -Djava.rmi.server.hostname=$4 \
                   -Dcom.sun.management.jmxremote.rmi.port=$5 \
                   /home/work/$2/target/$2.jar >>/dev/null &"
    source /etc/profile
    nohup java -jar -Dcom.sun.management.jmxremote  \
     -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false  \
     -Dspring.profiles.active=$3  \
     -Djava.rmi.server.hostname=$4  \
     -Dcom.sun.management.jmxremote.rmi.port=$5  \
	 -Dcom.sun.management.jmxremote.port=$5  \
    /home/work/$2/target/$2.jar >>/dev/null &
	#tail -f $2-nohup.out
	#nohup java -jar $base_path/$2/$2-1.0.0.jar &
}
main(){
	echo "启动部署脚本 $1 $2 $3 $4 $5"
	if test $1 = "deploy"
	then
		gitpull
		build $1 $2
		copy $1 $2
		stop $1 $2
		run $1 $2
    elif test $1 = "restart"
	then
		stop $1 $2
		run $1 $2 $3 $4 $5
    elif test $1 = "skip"
    then
        echo "跳过重启过程,不做任何事"
    elif test $1 = "stop"
    then
        stop $1 $2
        echo "关闭应用"
	else
          echoError $1
    fi

}

main $1 $2 $3 $4 $5