# ContactsList
通讯录实现，附带滚动侧栏、浮动标题头。

可复用为其他需要实现滑动标题头大列表控件。

![img]()

## FloatingBarItemDecoration

核心类，实现分组栏的滑动显示。

* onDraw()处理分组栏的绘制
* onDrawOver()处理浮动分组栏的绘制（屏幕最上方一组的分组栏）

## IndexBar

侧栏条