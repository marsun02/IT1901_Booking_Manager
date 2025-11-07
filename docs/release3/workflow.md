## Workflow for release 3

Throughout the project period, we held weekly meetings to update each other on our progress. These meetings were often more productive, as they allowed us to work together more effectively and help each other with coding tasks. For the third release, we met even more frequently and worked more intensively on the project. We also created and assigned more tasks on our project board, which helped us keep track of who was working on what and prevented unnecessary conflicts. 

Close to the submission deadline, we noticed an issue where one group member had been using different accounts. A personal account was logged in to VS Code and an NTNU account was logged into GitHub. This caused commits made through VS Code or the terminal to be linked to his personal account, while merges on GitHub were linked to the NTNU account. To fix this, he went through the entire Git log, found every instance of his personal username and email, and replaced them with his NTNU information. However, this created a new problem. A completely new commit history was generated. As a result of this, anyone who had branches based on the old history ran into issues when trying to make pull requests to merge into main. Our solution was to create new branches from the new commit history, and then rebase the old branches, which allowed us to merge properly into main again.

Despite these challenges, we worked together to find good solutions and maintain a steady workflow. Our communication and working together throughout the project helped us build and maintain a strong team dynamic.

**[Back to root README](../../README.md)**
