# ContactsList
An implementation of Android contacts list.

Can be easily applied to other list with floating section bar.

通讯录实现，附带滚动侧栏、浮动标题头。

可复用为其他需要实现滑动标题头大列表控件。

![img](https://github.com/hgDendi/ContactsList/blob/master/img/ContactsListDemo.gif)

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