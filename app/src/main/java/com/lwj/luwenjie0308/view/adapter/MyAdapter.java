package com.lwj.luwenjie0308.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lwj.luwenjie0308.R;
import com.lwj.luwenjie0308.model.bean.ShopBean;

/**
 * @Auther:
 * @Date: 2019/3/8 14:36
 * @Description:
 */
public class MyAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ShopBean bean;
    private CheckBox checkbox;

    public MyAdapter(Context mContext) {
        this.context = mContext;
    }

    public void setData(ShopBean shopBean) {
        Log.e("myMessage", "" + shopBean);
        this.bean = shopBean;
        notifyDataSetChanged();
    }

    public void setCheckBox(final CheckBox checkBox) {
        this.checkbox = checkBox;
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox1 = (CheckBox) v;
                selectAll(checkBox1.isChecked());
            }
        });
    }

    private void selectAll(boolean checked) {
        for (int i = 0; i < bean.getData().size(); i++) {
            ShopBean.DataBean dataBean = bean.getData().get(i);
            dataBean.setIscheck(checked);
            for (int j = 0; j < dataBean.getList().size(); j++) {
                ShopBean.DataBean.ListBean listBean = dataBean.getList().get(j);
                listBean.setCheck(checked);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (bean == null) {
            return 0;
        }
        return bean.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (bean == null) {
            return 0;
        }
        return bean.getData().get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder_Group holder_group;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group, null);
            holder_group = new ViewHolder_Group();
            holder_group.checkBox_group = convertView.findViewById(R.id.checkbox_group);
            holder_group.textView_group = convertView.findViewById(R.id.name_group);
            convertView.setTag(holder_group);
        } else {
            holder_group = (ViewHolder_Group) convertView.getTag();
        }

        holder_group.checkBox_group.setChecked(bean.getData().get(groupPosition).isIscheck());
        holder_group.textView_group.setText(bean.getData().get(groupPosition).getSellerName());
        holder_group.checkBox_group.setTag(groupPosition);
        holder_group.checkBox_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                int groupPosition = Integer.parseInt(checkBox.getTag().toString());
                bean.getData().get(groupPosition).setIscheck(checked);
                selectGroup(groupPosition, checked);
                boolean selectAllGroup = isSelectAllGroup();
                checkbox.setChecked(selectAllGroup);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private boolean isSelectAllGroup() {
        for (int i = 0; i < bean.getData().size(); i++) {
            ShopBean.DataBean dataBean = bean.getData().get(i);
            boolean ischeck = dataBean.isIscheck();
            if (!ischeck) {
                return false;
            }
        }
        return true;
    }

    private void selectGroup(int groupPosition, boolean checked) {
        for (int i = 0; i < bean.getData().get(groupPosition).getList().size(); i++) {
            ShopBean.DataBean.ListBean listBean = bean.getData().get(groupPosition).getList().get(i);
            listBean.setCheck(checked);
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder_Child holder_child;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child, null);
            holder_child = new ViewHolder_Child();
            holder_child.checkBox_child = convertView.findViewById(R.id.checkbox_child);
            holder_child.imageView_child = convertView.findViewById(R.id.image_child);
            holder_child.name = convertView.findViewById(R.id.title_child);
            holder_child.price = convertView.findViewById(R.id.price_child);
            convertView.setTag(holder_child);
        } else {
            holder_child = (ViewHolder_Child) convertView.getTag();
        }
        holder_child.name.setText(bean.getData().get(groupPosition).getList().get(childPosition).getTitle());
        holder_child.price.setText(bean.getData().get(groupPosition).getList().get(childPosition).getPrice() + "");
        holder_child.checkBox_child.setChecked(bean.getData().get(groupPosition).getList().get(childPosition).isCheck());
        Glide.with(context).load(bean.getData().get(groupPosition).getList().get(childPosition).getDetailUrl()).into(holder_child.imageView_child);
        holder_child.checkBox_child.setTag(groupPosition + "#" + childPosition);
        holder_child.checkBox_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                String tag = (String) checkBox.getTag();
                int groupPosition = Integer.parseInt(tag.split("#")[0]);
                int childPosition = Integer.parseInt(tag.split("#")[1]);
                ShopBean.DataBean dataBean = bean.getData().get(groupPosition);
                ShopBean.DataBean.ListBean listBean = dataBean.getList().get(childPosition);
                listBean.setCheck(checkBox.isChecked());
                boolean selectGroup = isSelectGroup(groupPosition);
                dataBean.setIscheck(selectGroup);
                boolean selectAllGroup = isSelectAllGroup();
                checkbox.setChecked(selectAllGroup);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private boolean isSelectGroup(int groupPosition) {
        for (int i = 0; i < bean.getData().get(groupPosition).getList().size(); i++) {
            ShopBean.DataBean.ListBean listBean = bean.getData().get(groupPosition).getList().get(i);
            boolean check = listBean.isCheck();
            if (!check) {
                return false;
            }
        }
        return true;
    }

    static class ViewHolder_Group {
        CheckBox checkBox_group;
        TextView textView_group;
    }

    static class ViewHolder_Child {
        CheckBox checkBox_child;
        TextView name, price;
        ImageView imageView_child;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
