package ie.ul.yunlonggao.indi;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class CostListAdapter extends BaseAdapter {

    private List<CostBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public CostListAdapter(Context context,List<CostBean> list){

        mList=list;
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
    }

    public int getCount(){
        return mList.size();
    }
    public Object getItem(int position){
        return mList.get(position);
    }
    public long getItemId(int position){
        return position;
    }
    public View getView(int position,View convertView,ViewGroup parent){

        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView=mLayoutInflater.inflate(R.layout.list_item,null);
            viewHolder.mTvCostTitle=(TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mTvCostDate=(TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.mTvCostMoney=(TextView) convertView.findViewById(R.id.tv_cost);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        CostBean bean=mList.get(position);
        viewHolder.mTvCostTitle.setText(bean.costTile);
        viewHolder.mTvCostDate.setText(bean.costDate);
        viewHolder.mTvCostMoney.setText(bean.costMoney);
        return convertView;
    }

    private static class ViewHolder{
        public TextView mTvCostTitle;
        public TextView mTvCostDate;
        public TextView mTvCostMoney;
    }

}
