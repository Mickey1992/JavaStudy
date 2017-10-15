Basis

### commit

`git commit`

### branch

- `git branch [new branch]`
- `git checkout [new branch]`

**or**

- `git checkout -b [new branch]`

### branch merge

1. use `merge`

   merge branchB into branchA

   `git checkout branchA`

   `git merge branchB`

2. use `rebase`

   实际上就是取出一系列的提交记录，“复制”它们，然后在另外一个地方逐个的放下去。Rebase 的优势就是可以创造更线性的提交历史

   `git checkout branchA`

   `git rebase master`

   把 branchA 分支里的工作直接移到 master 分支上。（branchA上做的所有变更都按照顺序放在master上所做的变更后面）

   `git checkout master`

   `git rebase branchA`

   把master指向branchA指向的位置


### 在提交树上移动

   `HEAD`总是指向当前分支上最近一次提交记录（通常情况下是指向分支名的）

   - 分离`HEAD`（让它指向某一个具体的提交记录而不是分支名）

      `git checkout [commit hash value]`

   - 相对引用

      使用 `^` 向上移动 1 个提交记录

      使用 `~<num>` 向上移动多个提交记录，如 ~3

      `git checkout HEAD^`

      `git checkout HEAD~3`

   - 强制修改branch的位置

      `git branch -f master HEAD~3`

### 撤销变更

1. `reset`（把分支记录回退几个提交记录）

   `git reset HEAD^`

2. `revert`(新建一个提交来撤销提交记录)

   `git revert HEAD^`

### 整理提交记录

1. `git cherry-pick [C1] [C2]`(把C1,C2两个提交记录加到当前分支)
2. 交互式`rebase`

   `git rebase -i HEAD~3`
