# ContactsList
An implementation of Android contacts list.

Can be easily applied to other list with floating section bar.

通讯录实现，附带滚动侧栏、浮动标题头。

可复用为其他需要实现滑动标题头的列表控件。

![img](https://github.com/hgDendi/ContactsList/blob/master/img/ContactsListDemo.gif)

![demo](https://github.com/hgDendi/ContactsList/blob/master/img/ContactsListDemo2.gif)

解析blog请见：[HgWorts](http://www.hgworts.tech/android/2017/01/04/Android%E9%80%9A%E8%AE%AF%E5%BD%95%E5%AE%9E%E7%8E%B0)

## FloatingBarItemDecoration

Key class which takes care of section bar of the list(RecyclerView)

- onDraw() 
  - handles the drawing of the section bar
- onDrawOver()
  - handles the drawing of the floating section bar(on top of the screen)

核心类，实现分组栏的滑动显示。

* onDraw()处理分组栏的绘制
* onDrawOver()处理浮动分组栏的绘制（屏幕最上方一组的分组栏）

## IndexBar

the bar aside

侧栏条

## Lisence

```
  Copyright 2017 ChenHao Dendi
 
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```