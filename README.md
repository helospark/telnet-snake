# Telnet snake (and honeypot)

## Introduction

A snake game that can be played via regular telnet. Also has a secondary function as a (basic) honeypot that collects IPs and user inputs.

![Sample gameplay](https://raw.githubusercontent.com/helospark/telnet-snake/master/documentation/telnet_snake.gif)

## Compilation & running

    mvn clean install

then you can run the jar in the target folder with

    java -jar telnet-snake-{VERSION}.jar

## Configuration

The default configurations are found in default_settings.properties.

## Installation

At the moment the installation can only be done manually, the following steps are needed:
  - For security user create a non privlidged user

     sudo useradd -m telnetsnake
     sudo passwd telnetsnake
     # enter some super secret password

  - Database is stored by default in /opt/telnet-snake/snake_game.db, create this folder and make it writable to the user who will run the telnet-snake application:

     sudo mkdir /opt/telnet-snake
     sudo chown telnetsnake:telnetsnake /opt/telnet-snake

  - Logs by defaults are stored at /var/log/telnet-snake, create this directory and make it writable by the user who will run the telnet-snake:

     sudo mkdir /var/log/telnet-snake
     sudo chown telnetsnake:telnetsnake /var/log/telnet-snake

  - For security reasons don't run the telnet-snake at an admin port, so you will need to create a redirect from telnet port:

      iptables -A PREROUTING -t nat -i eth0 -p tcp --dport 23 -j REDIRECT --to-port 2323

  - [OPTIONAL] If you want to override the settings, copy [properties file](https://github.com/helospark/telnet-snake/blob/master/src/main/resources/default_settings.properties) to /etc/telnet-snake/config.properties

     sudo mkdir /etc/telnet-snake
     sudo wget -O /etc/telnet-snake/config.properties https://raw.githubusercontent.com/helospark/telnet-snake/master/src/main/resources/default_settings.properties
     sudo chown telnetsnake:telnetsnake /etc/telnet-snake

  - Copy the executable jar created by mvn clean install to the directory where you want to run it:

       sudo cp ~/Download/telnet-snake-{version}.jar /opt/telnet-snake/telnet-snake.jar
       sudo chown telnetsnake:telnetsnake /opt/telnet-snake/telnet-snake.jar

  - Now you can run it. If you start it with ssh (or want to exit the process for some other reasons), make sure to `disown` the process.

        su telnetsnake
        cd /opt/telnet-snake
        java -jar telnet-snake.jar &
        disown -a

  - You can exit the session and the application will run in the background

         exit

## Troubleshooting

Your first (and usually the last) step should be to check logs:

         cat /var/log/telnet-snake/gameplay.log

## Integration with other programs