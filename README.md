# BackupWave - _sync and backup_

####

> :warning: __WARNING:__
> this software is till in early stages of development, so its strictly NOT reccomanded to use it in a real environment because it may have bugs and security vulnerability

###

## What is this?

#### This is a software that automates the backup and synchronization of directorys to make the organization of your personal or enterprise environment easyer and faster. It is made to provide a smooth and easy expirience. I would like to remind that this software is being born as a persnal project and that for now it is continuing as such, it is however being created with the idea that one day it coud end up in an enterprise environment. 

###

## How do I install it?

#### Coming soon

###

## Usage:

###

#### Arguments
| short arg | long arg   | option    | description                                      | needed |
|:----------|:-----------|:----------|:-------------------------------------------------|:-------|
| -d        | --debug    | 1         | debug mode 1 (opens port 4040 on the host)       | none   |
| -d        | --debug    | 2         | debug mode 2 (manual discovery mode)             | [ -t ] |
| -d        | --debug    | 3         | debug mode 3 (get local computer information)    | none   |
| -d        | --debug    | 4         | debug mode 4 (file fragmentation)                | [ -p ] |
| -d        | --debug    | 5         | debug mode 5 (directory fragmentation)           | [ -p ] |
| -a        | --add      | none      | add a new file or directory to stage             | [ -p ] |
| -d        | --delete   | none      | delete a file or directory from stage            | [ -p ] |
| -b        | --backup   | none      | backup all the files staged                      | none   |
| -b        | --backup   | none      | backup a file or a directory                     | [ -p ] |
| -r        | --recover  | none      | recover all the files staged                     | none   |
| -r        | --recover  | none      | recover a file or a directory                    | [ -p ] |
| -t        | --target   | address   | specify the target address of an operation       | none   |
| -p        | --path     | file/path | specify a file or directory path of an operation | none   |
