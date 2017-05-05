# Telnet snake (and honeypot)

## Introduction

A snake game that can be played via regular telnet. Also has a secondary function as a (basic) honeypot that collects IPs and user inputs.

![Sample gameplay](https://raw.githubusercontent.com/helospark/telnet-snake/master/documentation/telnet_snake.gif)

## Compilation & running

      mvn clean install

then you can run the jar in the target folder with

      java -jar telnet-snake-{VERSION}.jar start

### Usage

    java -jar jarfile [options] command [command options]
      Options:
        -h, --help
          Display usage
        -v, --version
          Display version
      Commands:
        start      Start server
          Usage: start
    
        sql      Run SQL commands
          Usage: sql [options]
            Options:
              -s, --sql
                SQL command, if absent interactive SQL terminal is shown
    
        display      Display games
          Usage: display [options]
            Options:
              -n, -l, --limit
                Number of games to display (if not specified all)
    
        toplist      Display toplist
          Usage: toplist [options]
            Options:
              -n, -l, --limit
                Number of entries to display
    
        stop      Stop the running server
          Usage: stop

### Example

To start the server

      java -jar telnet-snake-{VERSION}.jar start

To stop the server

      java -jar telnet-snake-{VERSION}.jar stop

To display all user inputs (could be a lot and you might want to limit it after a while):

      java -jar telnet-snake-{VERSION}.jar display

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
         sudo chown -R telnetsnake:telnetsnake /etc/telnet-snake
         # Modify the /etc/telnet-snake/config.properties file to your liking

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
         cat /var/log/telnet-snake/general.log

If there are no logs, make sure that `/var/log/telnet-snake/` location is writable by the user who is running the application.

## Integration with other programs

On **Linux** integration with the following tools are recommended:

### Logrotate

Logrotate is used to rotate logs after some time (or some filesize) to a different file, gzip and remove old logs
Logrotate should be on Linux by default, you can enable logrotate for telnet-snake by creating a file
`/etc/logrotate.d/telnetsnake`
with content:
[telnetsnake](https://raw.githubusercontent.com/helospark/telnet-snake/master/environment/logrotate/telnetsnake-logrotate.conf)

### Fail2Ban

Fail2ban can be used to automatically add IPs to your firewall, who try to abuse this service
(for example by trying to open too many connections), or continuously connecting to the service to
waste bandwidth.
Under environment/fail2ban folder I have defined 3 filters
 - Too many accepted connection (repeated disconnect - connect)
 - Too many denied connection (continuously connecting)
 - Too many exception (if a user found a way to get exceptions from this application, that's a problem, so we ban them)

Also append the content of jail.conf at the end of you fail2ban's jail.conf.

## Limitation

Telnet was not designed to play games on it, therefore the telnet client itself imposes some limitations. I observed the followings:

 - Linux telnet client buffers input, which means enter has to be pressed to input the new direction of the snake. But it also buffers the output so the game is stable.
 - Windows telnet on the other hand does not buffer, so you can press direction changing keys just like a real game, but it does not buffer output causing it to be flickery (as the lines jump up and down). Usually becomes less of an issue, after a couple of seconds.